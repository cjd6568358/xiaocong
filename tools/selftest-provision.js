'use strict';
/**
 * 配网客户端自检（不需要真机）
 *
 * 目的：验证 tools/provision.js 的判定链路是否可靠 —— 特别是
 *   退出码 0（接受） / 3（设备主动 NACK） / 2（超时） 三种结局能否被正确区分。
 * 这直接决定 tools/experiment-crt.ps1 的 crt 边界结论可不可信。
 *
 * 做法：把 tools/fake-plug-softap.js 当成"设备"，分别配置成
 *   无限制 / H1(固定缓冲 512B) / H2(非空即拒) 三种固件行为，
 *   然后跑同一套 crt 变体，比对退出码是否符合预期。
 *
 *   node tools/selftest-provision.js
 */
const { spawn } = require('child_process');
const path = require('path');

const NODE = process.execPath;
const ROOT = path.resolve(__dirname, '..');

const CASES = [
  { name: '无限制 + crt=""      (基准：应成功)',            env: {},                        crt: '',                 expect: 0 },
  { name: '无限制 + crt=600B    (无限制则大 crt 也应成功)', env: {},                        crt: 'X'.repeat(600),    expect: 0 },
  { name: 'H1(上限512) + crt=1B (小 crt 应被接受)',         env: { CRT_MAX: '512' },        crt: 'A',                expect: 0 },
  { name: 'H1(上限512) + crt=600B (超限应 NACK)',           env: { CRT_MAX: '512' },        crt: 'X'.repeat(600),    expect: 3 },
  { name: 'H2(非空即拒) + crt=1B (应 NACK)',                env: { CRT_EMPTY_ONLY: '1' },   crt: 'A',                expect: 3 },
  { name: 'H2(非空即拒) + crt="" (空 crt 应成功)',          env: { CRT_EMPTY_ONLY: '1' },   crt: '',                 expect: 0 },
];

const EXIT_NAME = { 0: '0 接受', 2: '2 超时', 3: '3 NACK' };

function startPlug(port, env) {
  return new Promise((resolve, reject) => {
    const p = spawn(NODE, [path.join(ROOT, 'tools/fake-plug-softap.js'), `--port=${port}`], {
      env: { ...process.env, ...env },
      cwd: ROOT,
    });
    let out = '';
    const to = setTimeout(() => reject(new Error('假设备启动超时')), 6000);
    p.stdout.on('data', (d) => {
      out += d.toString();
      if (out.includes('监听 UDP')) { clearTimeout(to); resolve(p); }
    });
    p.stderr.on('data', (d) => { out += d.toString(); });
    p.on('exit', (c) => { clearTimeout(to); reject(new Error(`假设备提前退出 code=${c}\n${out}`)); });
  });
}

function runClient(port, crt) {
  return new Promise((resolve) => {
    const p = spawn(NODE, [
      path.join(ROOT, 'tools/provision.js'),
      '--ssid=SELFTEST', '--pass=selftest123',
      '--host=127.0.0.1', `--port=${port}`,
      '--domain=192.168.1.20',
      '--timeout=6000', '--end-fallback=1500', '--cdata-retry=1',
    ], { env: { ...process.env, XC_CRT: crt }, cwd: ROOT });
    let out = '';
    p.stdout.on('data', (d) => { out += d.toString(); });
    p.stderr.on('data', (d) => { out += d.toString(); });
    p.on('exit', (code) => resolve({ code, out }));
  });
}

(async () => {
  console.log('===== 配网客户端自检（假设备）=====\n');
  let pass = 0, fail = 0;
  for (let i = 0; i < CASES.length; i++) {
    const c = CASES[i];
    const port = 5700 + i;
    let plug = null;
    try {
      plug = await startPlug(port, c.env);
      const { code, out } = await runClient(port, c.crt);
      const ok = code === c.expect;
      if (ok) pass++; else fail++;
      console.log(`${ok ? '✅' : '❌'} ${c.name}`);
      console.log(`     期望退出码 ${EXIT_NAME[c.expect] ?? c.expect}，实际 ${EXIT_NAME[code] ?? code}`);
      if (!ok) {
        console.log('     --- 客户端输出 ---');
        console.log(out.split('\n').map((l) => '     ' + l).join('\n'));
      }
      if (out.includes('NACK')) {
        const line = out.split('\n').find((l) => l.includes('NACK'));
        console.log(`     客户端 NACK 判定：${line.trim()}`);
      }
    } catch (e) {
      fail++;
      console.log(`❌ ${c.name}\n     ${e.message}`);
    } finally {
      if (plug) { try { plug.kill(); } catch (e) {} }
    }
    console.log('');
  }
  console.log(`===== 结果：通过 ${pass} / 失败 ${fail} =====`);
  process.exit(fail ? 1 : 0);
})();
