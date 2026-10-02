package com.facebook.react.flat;

import com.facebook.react.views.textinput.ReactTextInputManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RCTTextInputManager extends ReactTextInputManager {
    @Override // com.facebook.react.views.textinput.ReactTextInputManager, com.facebook.react.uimanager.ViewManager
    public RCTTextInput createShadowNodeInstance() {
        return new RCTTextInput();
    }

    @Override // com.facebook.react.views.textinput.ReactTextInputManager, com.facebook.react.uimanager.ViewManager
    public Class<RCTTextInput> getShadowNodeClass() {
        return RCTTextInput.class;
    }
}
