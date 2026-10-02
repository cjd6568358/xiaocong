package com.facebook.react.views.modal;

import android.graphics.Point;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ModalHostShadowNode extends LayoutShadowNode {
    ModalHostShadowNode() {
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void addChildAt(ReactShadowNode child, int i) {
        super.addChildAt(child, i);
        Point modalSize = ModalHostHelper.getModalHostSize(getThemedContext());
        child.setStyleWidth(modalSize.x);
        child.setStyleHeight(modalSize.y);
    }
}
