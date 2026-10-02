package com.facebook.react.shell;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.facebook.react.LazyReactPackage;
import com.facebook.react.animated.NativeAnimatedModule;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.flat.FlatARTSurfaceViewManager;
import com.facebook.react.flat.RCTImageViewManager;
import com.facebook.react.flat.RCTModalHostManager;
import com.facebook.react.flat.RCTRawTextManager;
import com.facebook.react.flat.RCTTextInlineImageManager;
import com.facebook.react.flat.RCTTextInputManager;
import com.facebook.react.flat.RCTTextManager;
import com.facebook.react.flat.RCTViewManager;
import com.facebook.react.flat.RCTViewPagerManager;
import com.facebook.react.flat.RCTVirtualTextManager;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.modules.appstate.AppStateModule;
import com.facebook.react.modules.camera.CameraRollManager;
import com.facebook.react.modules.camera.ImageEditingManager;
import com.facebook.react.modules.camera.ImageStoreManager;
import com.facebook.react.modules.clipboard.ClipboardModule;
import com.facebook.react.modules.datepicker.DatePickerDialogModule;
import com.facebook.react.modules.dialog.DialogModule;
import com.facebook.react.modules.fresco.FrescoModule;
import com.facebook.react.modules.i18nmanager.I18nManagerModule;
import com.facebook.react.modules.image.ImageLoaderModule;
import com.facebook.react.modules.intent.IntentModule;
import com.facebook.react.modules.location.LocationModule;
import com.facebook.react.modules.netinfo.NetInfoModule;
import com.facebook.react.modules.network.NetworkingModule;
import com.facebook.react.modules.permissions.PermissionsModule;
import com.facebook.react.modules.share.ShareModule;
import com.facebook.react.modules.statusbar.StatusBarModule;
import com.facebook.react.modules.storage.AsyncStorageModule;
import com.facebook.react.modules.timepicker.TimePickerDialogModule;
import com.facebook.react.modules.toast.ToastModule;
import com.facebook.react.modules.vibration.VibrationModule;
import com.facebook.react.modules.websocket.WebSocketModule;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.views.art.ARTRenderableViewManager;
import com.facebook.react.views.art.ARTSurfaceViewManager;
import com.facebook.react.views.drawer.ReactDrawerLayoutManager;
import com.facebook.react.views.image.ReactImageManager;
import com.facebook.react.views.modal.ReactModalHostManager;
import com.facebook.react.views.picker.ReactDialogPickerManager;
import com.facebook.react.views.picker.ReactDropdownPickerManager;
import com.facebook.react.views.progressbar.ReactProgressBarViewManager;
import com.facebook.react.views.scroll.ReactHorizontalScrollViewManager;
import com.facebook.react.views.scroll.ReactScrollViewManager;
import com.facebook.react.views.slider.ReactSliderManager;
import com.facebook.react.views.swiperefresh.SwipeRefreshLayoutManager;
import com.facebook.react.views.switchview.ReactSwitchManager;
import com.facebook.react.views.text.ReactRawTextManager;
import com.facebook.react.views.text.ReactTextViewManager;
import com.facebook.react.views.text.ReactVirtualTextViewManager;
import com.facebook.react.views.text.frescosupport.FrescoBasedReactTextInlineImageViewManager;
import com.facebook.react.views.textinput.ReactTextInputManager;
import com.facebook.react.views.toolbar.ReactToolbarManager;
import com.facebook.react.views.view.ReactViewManager;
import com.facebook.react.views.viewpager.ReactViewPagerManager;
import com.facebook.react.views.webview.ReactWebViewManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Provider;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MainReactPackage extends LazyReactPackage {
    private MainPackageConfig mConfig;

    @Override // com.facebook.react.LazyReactPackage
    public List<ModuleSpec> getNativeModules(final ReactApplicationContext context) {
        return Arrays.asList(new ModuleSpec(AppStateModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.1
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m69get() {
                return new AppStateModule(context);
            }
        }), new ModuleSpec(AsyncStorageModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.2
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m80get() {
                return new AsyncStorageModule(context);
            }
        }), new ModuleSpec(CameraRollManager.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.3
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m85get() {
                return new CameraRollManager(context);
            }
        }), new ModuleSpec(ClipboardModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.4
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m86get() {
                return new ClipboardModule(context);
            }
        }), new ModuleSpec(DatePickerDialogModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.5
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m87get() {
                return new DatePickerDialogModule(context);
            }
        }), new ModuleSpec(DialogModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.6
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m88get() {
                return new DialogModule(context);
            }
        }), new ModuleSpec(FrescoModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.7
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m89get() {
                return new FrescoModule(context, MainReactPackage.this.mConfig != null ? MainReactPackage.this.mConfig.getFrescoConfig() : null);
            }
        }), new ModuleSpec(I18nManagerModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.8
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m90get() {
                return new I18nManagerModule(context);
            }
        }), new ModuleSpec(ImageEditingManager.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.9
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m91get() {
                return new ImageEditingManager(context);
            }
        }), new ModuleSpec(ImageLoaderModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.10
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m70get() {
                return new ImageLoaderModule(context);
            }
        }), new ModuleSpec(ImageStoreManager.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.11
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m71get() {
                return new ImageStoreManager(context);
            }
        }), new ModuleSpec(IntentModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.12
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m72get() {
                return new IntentModule(context);
            }
        }), new ModuleSpec(LocationModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.13
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m73get() {
                return new LocationModule(context);
            }
        }), new ModuleSpec(NativeAnimatedModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.14
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m74get() {
                return new NativeAnimatedModule(context);
            }
        }), new ModuleSpec(NetworkingModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.15
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m75get() {
                return new NetworkingModule(context);
            }
        }), new ModuleSpec(NetInfoModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.16
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m76get() {
                return new NetInfoModule(context);
            }
        }), new ModuleSpec(PermissionsModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.17
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m77get() {
                return new PermissionsModule(context);
            }
        }), new ModuleSpec(ShareModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.18
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m78get() {
                return new ShareModule(context);
            }
        }), new ModuleSpec(StatusBarModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.19
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m79get() {
                return new StatusBarModule(context);
            }
        }), new ModuleSpec(TimePickerDialogModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.20
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m81get() {
                return new TimePickerDialogModule(context);
            }
        }), new ModuleSpec(ToastModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.21
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m82get() {
                return new ToastModule(context);
            }
        }), new ModuleSpec(VibrationModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.22
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m83get() {
                return new VibrationModule(context);
            }
        }), new ModuleSpec(WebSocketModule.class, new Provider<NativeModule>() { // from class: com.facebook.react.shell.MainReactPackage.23
            /* JADX INFO: renamed from: get, reason: merged with bridge method [inline-methods] */
            public NativeModule m84get() {
                return new WebSocketModule(context);
            }
        }));
    }

    @Override // com.facebook.react.ReactPackage
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override // com.facebook.react.LazyReactPackage, com.facebook.react.ReactPackage
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        List<ViewManager> viewManagers = new ArrayList<>();
        viewManagers.add(ARTRenderableViewManager.createARTGroupViewManager());
        viewManagers.add(ARTRenderableViewManager.createARTShapeViewManager());
        viewManagers.add(ARTRenderableViewManager.createARTTextViewManager());
        viewManagers.add(new ARTSurfaceViewManager());
        viewManagers.add(new ReactDialogPickerManager());
        viewManagers.add(new ReactDrawerLayoutManager());
        viewManagers.add(new ReactDropdownPickerManager());
        viewManagers.add(new ReactHorizontalScrollViewManager());
        viewManagers.add(new ReactImageManager());
        viewManagers.add(new ReactModalHostManager());
        viewManagers.add(new ReactProgressBarViewManager());
        viewManagers.add(new ReactRawTextManager());
        viewManagers.add(new ReactScrollViewManager());
        viewManagers.add(new ReactSliderManager());
        viewManagers.add(new ReactSwitchManager());
        viewManagers.add(new FrescoBasedReactTextInlineImageViewManager());
        viewManagers.add(new ReactTextInputManager());
        viewManagers.add(new ReactTextViewManager());
        viewManagers.add(new ReactToolbarManager());
        viewManagers.add(new ReactViewManager());
        viewManagers.add(new ReactViewPagerManager());
        viewManagers.add(new ReactVirtualTextViewManager());
        viewManagers.add(new ReactWebViewManager());
        viewManagers.add(new SwipeRefreshLayoutManager());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(reactContext);
        if (preferences.getBoolean("flat_uiimplementation", false)) {
            viewManagers.addAll(Arrays.asList(new RCTViewManager(), new RCTTextManager(), new RCTRawTextManager(), new RCTVirtualTextManager(), new RCTTextInlineImageManager(), new RCTImageViewManager(), new RCTTextInputManager(), new RCTViewPagerManager(), new FlatARTSurfaceViewManager(), new RCTModalHostManager()));
        }
        return viewManagers;
    }

    @Override // com.facebook.react.LazyReactPackage
    public ReactModuleInfoProvider getReactModuleInfoProvider() {
        return LazyReactPackage.getReactModuleInfoProviderViaReflection(this);
    }
}
