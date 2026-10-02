package com.youzan.jsbridge.entrance;

import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class JsBridgeEntrance {
    protected abstract String getEntrance();

    protected abstract Set<String> getMethods();

    private String formatMethods(Set<String> methods) {
        StringBuilder sb = new StringBuilder();
        for (String item : methods) {
            sb.append(String.format("=a.%s", item));
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    public String toJavaScript() {
        return "javascript:(function(b){console.log(\"JSBridge initialization\");var a={queue:[],callback:function(){var d=Array.prototype.slice.call(arguments,0);var c=d.shift();var e=d.shift();this.queue[c].apply(this,d);if(!e){delete this.queue[c]}}};" + formatMethods(getMethods()) + "=function(){var f=Array.prototype.slice.call(arguments,0);if(f.length<1){throw\"miss method name\"}var e=[];for(var h=1;h<f.length;h++){var c=f[h];var j=typeof c;e[e.length]=j;if(j==\"function\"){var d=a.queue.length;a.queue[d]=c;f[h]=d}}var g=JSON.parse(prompt(JSON.stringify({method:f.shift(),types:e,args:f})));return g.result};Object.getOwnPropertyNames(a).forEach(function(d){var c=a[d];if(typeof c===\"function\"&&d!==\"callback\"){a[d]=function(){return c.apply(a,[d].concat(Array.prototype.slice.call(arguments,0)))}}});b." + getEntrance() + "=a;console.log(\"JSBridge Is Ready\");})(window);";
    }
}
