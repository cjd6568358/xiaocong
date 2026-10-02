package android.support.v4.media.session;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;
import android.view.KeyEvent;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class MediaControllerCompat {
    private final MediaControllerImpl mImpl;
    private final HashSet<Callback> mRegisteredCallbacks = new HashSet<>();
    private final MediaSessionCompat.Token mToken;

    interface MediaControllerImpl {
        boolean dispatchMediaButtonEvent(KeyEvent keyEvent);
    }

    public MediaControllerCompat(Context context, MediaSessionCompat.Token sessionToken) throws RemoteException {
        if (sessionToken == null) {
            throw new IllegalArgumentException("sessionToken must not be null");
        }
        this.mToken = sessionToken;
        if (Build.VERSION.SDK_INT >= 24) {
            this.mImpl = new MediaControllerImplApi24(context, sessionToken);
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaControllerImplApi23(context, sessionToken);
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaControllerImplApi21(context, sessionToken);
        } else {
            this.mImpl = new MediaControllerImplBase(this.mToken);
        }
    }

    public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
        if (keyEvent == null) {
            throw new IllegalArgumentException("KeyEvent may not be null");
        }
        return this.mImpl.dispatchMediaButtonEvent(keyEvent);
    }

    public static abstract class Callback implements IBinder.DeathRecipient {
        private final Object mCallbackObj;
        MessageHandler mHandler;
        boolean mHasExtraCallback;

        public Callback() {
            if (Build.VERSION.SDK_INT >= 21) {
                this.mCallbackObj = MediaControllerCompatApi21.createCallback(new StubApi21(this));
            } else {
                this.mCallbackObj = new StubCompat(this);
            }
        }

        public void onSessionDestroyed() {
        }

        public void onSessionEvent(String event, Bundle extras) {
        }

        public void onPlaybackStateChanged(PlaybackStateCompat state) {
        }

        public void onMetadataChanged(MediaMetadataCompat metadata) {
        }

        public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) {
        }

        public void onQueueTitleChanged(CharSequence title) {
        }

        public void onExtrasChanged(Bundle extras) {
        }

        public void onAudioInfoChanged(PlaybackInfo info) {
        }

        public void onCaptioningEnabledChanged(boolean enabled) {
        }

        public void onRepeatModeChanged(int repeatMode) {
        }

        @Deprecated
        public void onShuffleModeChanged(boolean enabled) {
        }

        public void onShuffleModeChanged(int shuffleMode) {
        }

        void postToHandler(int what, Object obj, Bundle data) {
            if (this.mHandler != null) {
                Message msg = this.mHandler.obtainMessage(what, obj);
                msg.setData(data);
                msg.sendToTarget();
            }
        }

        private static class StubApi21 implements MediaControllerCompatApi21.Callback {
            private final WeakReference<Callback> mCallback;

            StubApi21(Callback callback) {
                this.mCallback = new WeakReference<>(callback);
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onSessionDestroyed() {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.onSessionDestroyed();
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onSessionEvent(String event, Bundle extras) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    if (!callback.mHasExtraCallback || Build.VERSION.SDK_INT >= 23) {
                        callback.onSessionEvent(event, extras);
                    }
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onPlaybackStateChanged(Object stateObj) {
                Callback callback = this.mCallback.get();
                if (callback != null && !callback.mHasExtraCallback) {
                    callback.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(stateObj));
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onMetadataChanged(Object metadataObj) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(metadataObj));
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onQueueChanged(List<?> queue) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.onQueueChanged(MediaSessionCompat.QueueItem.fromQueueItemList(queue));
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onQueueTitleChanged(CharSequence title) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.onQueueTitleChanged(title);
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onExtrasChanged(Bundle extras) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.onExtrasChanged(extras);
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onAudioInfoChanged(int type, int stream, int control, int max, int current) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.onAudioInfoChanged(new PlaybackInfo(type, stream, control, max, current));
                }
            }
        }

        private static class StubCompat extends IMediaControllerCallback.Stub {
            private final WeakReference<Callback> mCallback;

            StubCompat(Callback callback) {
                this.mCallback = new WeakReference<>(callback);
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onEvent(String event, Bundle extras) throws RemoteException {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(1, event, extras);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onSessionDestroyed() throws RemoteException {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(8, null, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onPlaybackStateChanged(PlaybackStateCompat state) throws RemoteException {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(2, state, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onMetadataChanged(MediaMetadataCompat metadata) throws RemoteException {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(3, metadata, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) throws RemoteException {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(5, queue, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onQueueTitleChanged(CharSequence title) throws RemoteException {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(6, title, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onCaptioningEnabledChanged(boolean enabled) throws RemoteException {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(11, Boolean.valueOf(enabled), null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onRepeatModeChanged(int repeatMode) throws RemoteException {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(9, Integer.valueOf(repeatMode), null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onShuffleModeChangedDeprecated(boolean enabled) throws RemoteException {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(10, Boolean.valueOf(enabled), null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onShuffleModeChanged(int shuffleMode) throws RemoteException {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(12, Integer.valueOf(shuffleMode), null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onExtrasChanged(Bundle extras) throws RemoteException {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(7, extras, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onVolumeInfoChanged(ParcelableVolumeInfo info) throws RemoteException {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    PlaybackInfo pi = null;
                    if (info != null) {
                        pi = new PlaybackInfo(info.volumeType, info.audioStream, info.controlType, info.maxVolume, info.currentVolume);
                    }
                    callback.postToHandler(4, pi, null);
                }
            }
        }

        private class MessageHandler extends Handler {
            boolean mRegistered;
            final /* synthetic */ Callback this$0;

            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (this.mRegistered) {
                    switch (msg.what) {
                        case 1:
                            this.this$0.onSessionEvent((String) msg.obj, msg.getData());
                            break;
                        case 2:
                            this.this$0.onPlaybackStateChanged((PlaybackStateCompat) msg.obj);
                            break;
                        case 3:
                            this.this$0.onMetadataChanged((MediaMetadataCompat) msg.obj);
                            break;
                        case 4:
                            this.this$0.onAudioInfoChanged((PlaybackInfo) msg.obj);
                            break;
                        case 5:
                            this.this$0.onQueueChanged((List) msg.obj);
                            break;
                        case 6:
                            this.this$0.onQueueTitleChanged((CharSequence) msg.obj);
                            break;
                        case 7:
                            this.this$0.onExtrasChanged((Bundle) msg.obj);
                            break;
                        case 8:
                            this.this$0.onSessionDestroyed();
                            break;
                        case 9:
                            this.this$0.onRepeatModeChanged(((Integer) msg.obj).intValue());
                            break;
                        case 10:
                            this.this$0.onShuffleModeChanged(((Boolean) msg.obj).booleanValue());
                            break;
                        case 11:
                            this.this$0.onCaptioningEnabledChanged(((Boolean) msg.obj).booleanValue());
                            break;
                        case 12:
                            this.this$0.onShuffleModeChanged(((Integer) msg.obj).intValue());
                            break;
                    }
                }
            }
        }
    }

    public static final class PlaybackInfo {
        private final int mAudioStream;
        private final int mCurrentVolume;
        private final int mMaxVolume;
        private final int mPlaybackType;
        private final int mVolumeControl;

        PlaybackInfo(int type, int stream, int control, int max, int current) {
            this.mPlaybackType = type;
            this.mAudioStream = stream;
            this.mVolumeControl = control;
            this.mMaxVolume = max;
            this.mCurrentVolume = current;
        }
    }

    static class MediaControllerImplBase implements MediaControllerImpl {
        private IMediaSession mBinder;

        public MediaControllerImplBase(MediaSessionCompat.Token token) {
            this.mBinder = IMediaSession.Stub.asInterface((IBinder) token.getToken());
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public boolean dispatchMediaButtonEvent(KeyEvent event) {
            if (event == null) {
                throw new IllegalArgumentException("event may not be null.");
            }
            try {
                this.mBinder.sendMediaButton(event);
                return false;
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in dispatchMediaButtonEvent.", e);
                return false;
            }
        }
    }

    static class MediaControllerImplApi21 implements MediaControllerImpl {
        protected final Object mControllerObj;
        private IMediaSession mExtraBinder;
        private final List<Callback> mPendingCallbacks = new ArrayList();
        private HashMap<Callback, ExtraCallback> mCallbackMap = new HashMap<>();

        public MediaControllerImplApi21(Context context, MediaSessionCompat.Token sessionToken) throws RemoteException {
            this.mControllerObj = MediaControllerCompatApi21.fromToken(context, sessionToken.getToken());
            if (this.mControllerObj == null) {
                throw new RemoteException();
            }
            this.mExtraBinder = sessionToken.getExtraBinder();
            if (this.mExtraBinder == null) {
                requestExtraBinder();
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public boolean dispatchMediaButtonEvent(KeyEvent event) {
            return MediaControllerCompatApi21.dispatchMediaButtonEvent(this.mControllerObj, event);
        }

        public void sendCommand(String command, Bundle params, ResultReceiver cb) {
            MediaControllerCompatApi21.sendCommand(this.mControllerObj, command, params, cb);
        }

        private void requestExtraBinder() {
            sendCommand("android.support.v4.media.session.command.GET_EXTRA_BINDER", null, new ExtraBinderRequestResultReceiver(this, new Handler()));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void processPendingCallbacks() {
            if (this.mExtraBinder != null) {
                synchronized (this.mPendingCallbacks) {
                    for (Callback callback : this.mPendingCallbacks) {
                        ExtraCallback extraCallback = new ExtraCallback(callback);
                        this.mCallbackMap.put(callback, extraCallback);
                        callback.mHasExtraCallback = true;
                        try {
                            this.mExtraBinder.registerCallbackListener(extraCallback);
                        } catch (RemoteException e) {
                            Log.e("MediaControllerCompat", "Dead object in registerCallback.", e);
                            this.mPendingCallbacks.clear();
                        }
                    }
                    this.mPendingCallbacks.clear();
                }
            }
        }

        private static class ExtraBinderRequestResultReceiver extends ResultReceiver {
            private WeakReference<MediaControllerImplApi21> mMediaControllerImpl;

            public ExtraBinderRequestResultReceiver(MediaControllerImplApi21 mediaControllerImpl, Handler handler) {
                super(handler);
                this.mMediaControllerImpl = new WeakReference<>(mediaControllerImpl);
            }

            @Override // android.os.ResultReceiver
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                MediaControllerImplApi21 mediaControllerImpl = this.mMediaControllerImpl.get();
                if (mediaControllerImpl != null && resultData != null) {
                    mediaControllerImpl.mExtraBinder = IMediaSession.Stub.asInterface(BundleCompat.getBinder(resultData, "android.support.v4.media.session.EXTRA_BINDER"));
                    mediaControllerImpl.processPendingCallbacks();
                }
            }
        }

        private static class ExtraCallback extends Callback.StubCompat {
            ExtraCallback(Callback callback) {
                super(callback);
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public void onSessionDestroyed() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public void onMetadataChanged(MediaMetadataCompat metadata) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public void onQueueTitleChanged(CharSequence title) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public void onExtrasChanged(Bundle extras) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public void onVolumeInfoChanged(ParcelableVolumeInfo info) throws RemoteException {
                throw new AssertionError();
            }
        }
    }

    static class MediaControllerImplApi23 extends MediaControllerImplApi21 {
        public MediaControllerImplApi23(Context context, MediaSessionCompat.Token sessionToken) throws RemoteException {
            super(context, sessionToken);
        }
    }

    static class MediaControllerImplApi24 extends MediaControllerImplApi23 {
        public MediaControllerImplApi24(Context context, MediaSessionCompat.Token sessionToken) throws RemoteException {
            super(context, sessionToken);
        }
    }
}
