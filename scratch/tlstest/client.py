import socket, ssl, sys
ctx = ssl.SSLContext(ssl.PROTOCOL_TLS_CLIENT)
ctx.minimum_version = ssl.TLSVersion.TLSv1
ctx.maximum_version = ssl.TLSVersion.TLSv1_1
ctx.set_ciphers("AES256-SHA:@SECLEVEL=0")
ctx.check_hostname = False
ctx.verify_mode = ssl.CERT_NONE
s = socket.create_connection(("127.0.0.1", 18999), timeout=8)
try:
    t = ctx.wrap_socket(s)
    print("客户端握手成功:", t.version(), t.cipher())
    t.sendall(b"hello-from-python-client")
    t.close()
except Exception as e:
    print("客户端握手失败:", type(e).__name__, e)
