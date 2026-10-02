package com.facebook.react.animated;

import android.util.SparseArray;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.UIImplementation;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcherListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class NativeAnimatedNodesManager implements EventDispatcherListener {
    private final Map<String, Map<String, String>> mCustomEventTypes;
    private final UIImplementation mUIImplementation;
    private final SparseArray<AnimatedNode> mAnimatedNodes = new SparseArray<>();
    private final SparseArray<AnimationDriver> mActiveAnimations = new SparseArray<>();
    private final SparseArray<AnimatedNode> mUpdatedNodes = new SparseArray<>();
    private final Map<String, EventAnimationDriver> mEventDrivers = new HashMap();
    private int mAnimatedGraphBFSColor = 0;

    public NativeAnimatedNodesManager(UIManagerModule uiManager) {
        this.mUIImplementation = uiManager.getUIImplementation();
        uiManager.getEventDispatcher().addListener(this);
        Object customEventTypes = ((Map) Assertions.assertNotNull(uiManager.getConstants())).get("customDirectEventTypes");
        this.mCustomEventTypes = (Map) customEventTypes;
    }

    AnimatedNode getNodeById(int id) {
        return this.mAnimatedNodes.get(id);
    }

    public boolean hasActiveAnimations() {
        return this.mActiveAnimations.size() > 0 || this.mUpdatedNodes.size() > 0;
    }

    public void createAnimatedNode(int tag, ReadableMap config) {
        AnimatedNode node;
        if (this.mAnimatedNodes.get(tag) != null) {
            throw new JSApplicationIllegalArgumentException("Animated node with tag " + tag + " already exists");
        }
        String type = config.getString("type");
        if ("style".equals(type)) {
            node = new StyleAnimatedNode(config, this);
        } else if ("value".equals(type)) {
            node = new ValueAnimatedNode(config);
        } else if ("props".equals(type)) {
            node = new PropsAnimatedNode(config, this);
        } else if ("interpolation".equals(type)) {
            node = new InterpolationAnimatedNode(config);
        } else if ("addition".equals(type)) {
            node = new AdditionAnimatedNode(config, this);
        } else if ("division".equals(type)) {
            node = new DivisionAnimatedNode(config, this);
        } else if ("multiplication".equals(type)) {
            node = new MultiplicationAnimatedNode(config, this);
        } else if ("modulus".equals(type)) {
            node = new ModulusAnimatedNode(config, this);
        } else if ("diffclamp".equals(type)) {
            node = new DiffClampAnimatedNode(config, this);
        } else if ("transform".equals(type)) {
            node = new TransformAnimatedNode(config, this);
        } else {
            throw new JSApplicationIllegalArgumentException("Unsupported node type: " + type);
        }
        node.mTag = tag;
        this.mAnimatedNodes.put(tag, node);
        this.mUpdatedNodes.put(tag, node);
    }

    public void dropAnimatedNode(int tag) {
        this.mAnimatedNodes.remove(tag);
        this.mUpdatedNodes.remove(tag);
    }

    public void startListeningToAnimatedNodeValue(int tag, AnimatedNodeValueListener listener) {
        AnimatedNode node = this.mAnimatedNodes.get(tag);
        if (node == null || !(node instanceof ValueAnimatedNode)) {
            throw new JSApplicationIllegalArgumentException("Animated node with tag " + tag + " does not exists or is not a 'value' node");
        }
        ((ValueAnimatedNode) node).setValueListener(listener);
    }

    public void stopListeningToAnimatedNodeValue(int tag) {
        AnimatedNode node = this.mAnimatedNodes.get(tag);
        if (node == null || !(node instanceof ValueAnimatedNode)) {
            throw new JSApplicationIllegalArgumentException("Animated node with tag " + tag + " does not exists or is not a 'value' node");
        }
        ((ValueAnimatedNode) node).setValueListener(null);
    }

    public void setAnimatedNodeValue(int tag, double value) {
        AnimatedNode node = this.mAnimatedNodes.get(tag);
        if (node == null || !(node instanceof ValueAnimatedNode)) {
            throw new JSApplicationIllegalArgumentException("Animated node with tag " + tag + " does not exists or is not a 'value' node");
        }
        ((ValueAnimatedNode) node).mValue = value;
        this.mUpdatedNodes.put(tag, node);
    }

    public void setAnimatedNodeOffset(int tag, double offset) {
        AnimatedNode node = this.mAnimatedNodes.get(tag);
        if (node == null || !(node instanceof ValueAnimatedNode)) {
            throw new JSApplicationIllegalArgumentException("Animated node with tag " + tag + " does not exists or is not a 'value' node");
        }
        ((ValueAnimatedNode) node).mOffset = offset;
        this.mUpdatedNodes.put(tag, node);
    }

    public void flattenAnimatedNodeOffset(int tag) {
        AnimatedNode node = this.mAnimatedNodes.get(tag);
        if (node == null || !(node instanceof ValueAnimatedNode)) {
            throw new JSApplicationIllegalArgumentException("Animated node with tag " + tag + " does not exists or is not a 'value' node");
        }
        ((ValueAnimatedNode) node).flattenOffset();
    }

    public void extractAnimatedNodeOffset(int tag) {
        AnimatedNode node = this.mAnimatedNodes.get(tag);
        if (node == null || !(node instanceof ValueAnimatedNode)) {
            throw new JSApplicationIllegalArgumentException("Animated node with tag " + tag + " does not exists or is not a 'value' node");
        }
        ((ValueAnimatedNode) node).extractOffset();
    }

    public void startAnimatingNode(int animationId, int animatedNodeTag, ReadableMap animationConfig, Callback endCallback) {
        AnimationDriver animation;
        AnimatedNode node = this.mAnimatedNodes.get(animatedNodeTag);
        if (node == null) {
            throw new JSApplicationIllegalArgumentException("Animated node with tag " + animatedNodeTag + " does not exists");
        }
        if (!(node instanceof ValueAnimatedNode)) {
            throw new JSApplicationIllegalArgumentException("Animated node should be of type " + ValueAnimatedNode.class.getName());
        }
        String type = animationConfig.getString("type");
        if ("frames".equals(type)) {
            animation = new FrameBasedAnimationDriver(animationConfig);
        } else if ("spring".equals(type)) {
            animation = new SpringAnimation(animationConfig);
        } else if ("decay".equals(type)) {
            animation = new DecayAnimation(animationConfig);
        } else {
            throw new JSApplicationIllegalArgumentException("Unsupported animation type: " + type);
        }
        animation.mId = animationId;
        animation.mEndCallback = endCallback;
        animation.mAnimatedValue = (ValueAnimatedNode) node;
        this.mActiveAnimations.put(animationId, animation);
    }

    public void stopAnimation(int animationId) {
        for (int i = 0; i < this.mActiveAnimations.size(); i++) {
            AnimationDriver animation = this.mActiveAnimations.valueAt(i);
            if (animation.mId == animationId) {
                WritableMap endCallbackResponse = Arguments.createMap();
                endCallbackResponse.putBoolean("finished", false);
                animation.mEndCallback.invoke(endCallbackResponse);
                this.mActiveAnimations.removeAt(i);
                return;
            }
        }
    }

    public void connectAnimatedNodes(int parentNodeTag, int childNodeTag) {
        AnimatedNode parentNode = this.mAnimatedNodes.get(parentNodeTag);
        if (parentNode == null) {
            throw new JSApplicationIllegalArgumentException("Animated node with tag " + parentNodeTag + " does not exists");
        }
        AnimatedNode childNode = this.mAnimatedNodes.get(childNodeTag);
        if (childNode == null) {
            throw new JSApplicationIllegalArgumentException("Animated node with tag " + childNodeTag + " does not exists");
        }
        parentNode.addChild(childNode);
        this.mUpdatedNodes.put(childNodeTag, childNode);
    }

    public void disconnectAnimatedNodes(int parentNodeTag, int childNodeTag) {
        AnimatedNode parentNode = this.mAnimatedNodes.get(parentNodeTag);
        if (parentNode == null) {
            throw new JSApplicationIllegalArgumentException("Animated node with tag " + parentNodeTag + " does not exists");
        }
        AnimatedNode childNode = this.mAnimatedNodes.get(childNodeTag);
        if (childNode == null) {
            throw new JSApplicationIllegalArgumentException("Animated node with tag " + childNodeTag + " does not exists");
        }
        parentNode.removeChild(childNode);
        this.mUpdatedNodes.put(childNodeTag, childNode);
    }

    public void connectAnimatedNodeToView(int animatedNodeTag, int viewTag) {
        AnimatedNode node = this.mAnimatedNodes.get(animatedNodeTag);
        if (node == null) {
            throw new JSApplicationIllegalArgumentException("Animated node with tag " + animatedNodeTag + " does not exists");
        }
        if (!(node instanceof PropsAnimatedNode)) {
            throw new JSApplicationIllegalArgumentException("Animated node connected to view should beof type " + PropsAnimatedNode.class.getName());
        }
        PropsAnimatedNode propsAnimatedNode = (PropsAnimatedNode) node;
        if (propsAnimatedNode.mConnectedViewTag != -1) {
            throw new JSApplicationIllegalArgumentException("Animated node " + animatedNodeTag + " is already attached to a view");
        }
        propsAnimatedNode.mConnectedViewTag = viewTag;
        this.mUpdatedNodes.put(animatedNodeTag, node);
    }

    public void disconnectAnimatedNodeFromView(int animatedNodeTag, int viewTag) {
        AnimatedNode node = this.mAnimatedNodes.get(animatedNodeTag);
        if (node == null) {
            throw new JSApplicationIllegalArgumentException("Animated node with tag " + animatedNodeTag + " does not exists");
        }
        if (!(node instanceof PropsAnimatedNode)) {
            throw new JSApplicationIllegalArgumentException("Animated node connected to view should beof type " + PropsAnimatedNode.class.getName());
        }
        PropsAnimatedNode propsAnimatedNode = (PropsAnimatedNode) node;
        if (propsAnimatedNode.mConnectedViewTag != viewTag) {
            throw new JSApplicationIllegalArgumentException("Attempting to disconnect view that has not been connected with the given animated node");
        }
        propsAnimatedNode.mConnectedViewTag = -1;
    }

    public void addAnimatedEventToView(int viewTag, String eventName, ReadableMap eventMapping) {
        int nodeTag = eventMapping.getInt("animatedValueTag");
        AnimatedNode node = this.mAnimatedNodes.get(nodeTag);
        if (node == null) {
            throw new JSApplicationIllegalArgumentException("Animated node with tag " + nodeTag + " does not exists");
        }
        if (!(node instanceof ValueAnimatedNode)) {
            throw new JSApplicationIllegalArgumentException("Animated node connected to event should beof type " + ValueAnimatedNode.class.getName());
        }
        ReadableArray path = eventMapping.getArray("nativeEventPath");
        List<String> pathList = new ArrayList<>(path.size());
        for (int i = 0; i < path.size(); i++) {
            pathList.add(path.getString(i));
        }
        EventAnimationDriver event = new EventAnimationDriver(pathList, (ValueAnimatedNode) node);
        this.mEventDrivers.put(viewTag + eventName, event);
    }

    public void removeAnimatedEventFromView(int viewTag, String eventName) {
        this.mEventDrivers.remove(viewTag + eventName);
    }

    @Override // com.facebook.react.uimanager.events.EventDispatcherListener
    public void onEventDispatch(Event event) {
        if (UiThreadUtil.isOnUiThread() && !this.mEventDrivers.isEmpty()) {
            String eventName = event.getEventName();
            Map<String, String> customEventType = this.mCustomEventTypes.get(eventName);
            if (customEventType != null) {
                eventName = customEventType.get("registrationName");
            }
            EventAnimationDriver eventDriver = this.mEventDrivers.get(event.getViewTag() + eventName);
            if (eventDriver != null) {
                event.dispatch(eventDriver);
                this.mUpdatedNodes.put(eventDriver.mValueNode.mTag, eventDriver.mValueNode);
            }
        }
    }

    public void runUpdates(long frameTimeNanos) {
        UiThreadUtil.assertOnUiThread();
        int activeNodesCount = 0;
        int updatedNodesCount = 0;
        boolean hasFinishedAnimations = false;
        this.mAnimatedGraphBFSColor++;
        if (this.mAnimatedGraphBFSColor == 0) {
            this.mAnimatedGraphBFSColor++;
        }
        Queue<AnimatedNode> nodesQueue = new ArrayDeque<>();
        for (int i = 0; i < this.mUpdatedNodes.size(); i++) {
            AnimatedNode node = this.mUpdatedNodes.valueAt(i);
            if (node.mBFSColor != this.mAnimatedGraphBFSColor) {
                node.mBFSColor = this.mAnimatedGraphBFSColor;
                activeNodesCount++;
                nodesQueue.add(node);
            }
        }
        for (int i2 = 0; i2 < this.mActiveAnimations.size(); i2++) {
            AnimationDriver animation = this.mActiveAnimations.valueAt(i2);
            animation.runAnimationStep(frameTimeNanos);
            AnimatedNode valueNode = animation.mAnimatedValue;
            if (valueNode.mBFSColor != this.mAnimatedGraphBFSColor) {
                valueNode.mBFSColor = this.mAnimatedGraphBFSColor;
                activeNodesCount++;
                nodesQueue.add(valueNode);
            }
            if (animation.mHasFinished) {
                hasFinishedAnimations = true;
            }
        }
        while (!nodesQueue.isEmpty()) {
            AnimatedNode nextNode = nodesQueue.poll();
            if (nextNode.mChildren != null) {
                for (int i3 = 0; i3 < nextNode.mChildren.size(); i3++) {
                    AnimatedNode child = nextNode.mChildren.get(i3);
                    child.mActiveIncomingNodes++;
                    if (child.mBFSColor != this.mAnimatedGraphBFSColor) {
                        child.mBFSColor = this.mAnimatedGraphBFSColor;
                        activeNodesCount++;
                        nodesQueue.add(child);
                    }
                }
            }
        }
        this.mAnimatedGraphBFSColor++;
        if (this.mAnimatedGraphBFSColor == 0) {
            this.mAnimatedGraphBFSColor++;
        }
        for (int i4 = 0; i4 < this.mUpdatedNodes.size(); i4++) {
            AnimatedNode node2 = this.mUpdatedNodes.valueAt(i4);
            if (node2.mActiveIncomingNodes == 0 && node2.mBFSColor != this.mAnimatedGraphBFSColor) {
                node2.mBFSColor = this.mAnimatedGraphBFSColor;
                updatedNodesCount++;
                nodesQueue.add(node2);
            }
        }
        for (int i5 = 0; i5 < this.mActiveAnimations.size(); i5++) {
            AnimatedNode valueNode2 = this.mActiveAnimations.valueAt(i5).mAnimatedValue;
            if (valueNode2.mActiveIncomingNodes == 0 && valueNode2.mBFSColor != this.mAnimatedGraphBFSColor) {
                valueNode2.mBFSColor = this.mAnimatedGraphBFSColor;
                updatedNodesCount++;
                nodesQueue.add(valueNode2);
            }
        }
        while (!nodesQueue.isEmpty()) {
            AnimatedNode nextNode2 = nodesQueue.poll();
            nextNode2.update();
            if (nextNode2 instanceof PropsAnimatedNode) {
                try {
                    ((PropsAnimatedNode) nextNode2).updateView(this.mUIImplementation);
                } catch (IllegalViewOperationException e) {
                    FLog.e("React", "Native animation workaround, frame lost as result of race condition", e);
                }
            }
            if (nextNode2 instanceof ValueAnimatedNode) {
                ((ValueAnimatedNode) nextNode2).onValueUpdate();
            }
            if (nextNode2.mChildren != null) {
                for (int i6 = 0; i6 < nextNode2.mChildren.size(); i6++) {
                    AnimatedNode child2 = nextNode2.mChildren.get(i6);
                    child2.mActiveIncomingNodes--;
                    if (child2.mBFSColor != this.mAnimatedGraphBFSColor && child2.mActiveIncomingNodes == 0) {
                        child2.mBFSColor = this.mAnimatedGraphBFSColor;
                        updatedNodesCount++;
                        nodesQueue.add(child2);
                    }
                }
            }
        }
        if (activeNodesCount != updatedNodesCount) {
            throw new IllegalStateException("Looks like animated nodes graph has cycles, there are " + activeNodesCount + " but toposort visited only " + updatedNodesCount);
        }
        this.mUpdatedNodes.clear();
        if (hasFinishedAnimations) {
            for (int i7 = this.mActiveAnimations.size() - 1; i7 >= 0; i7--) {
                AnimationDriver animation2 = this.mActiveAnimations.valueAt(i7);
                if (animation2.mHasFinished) {
                    WritableMap endCallbackResponse = Arguments.createMap();
                    endCallbackResponse.putBoolean("finished", true);
                    animation2.mEndCallback.invoke(endCallbackResponse);
                    this.mActiveAnimations.removeAt(i7);
                }
            }
        }
    }
}
