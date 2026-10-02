package com.facebook.imagepipeline.producers;

import android.util.Pair;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Sets;
import com.facebook.imagepipeline.common.Priority;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class MultiplexProducer<K, T extends Closeable> implements Producer<T> {
    private final Producer<T> mInputProducer;
    final Map<K, MultiplexProducer<K, T>.Multiplexer> mMultiplexers = new HashMap();

    protected abstract T cloneOrNull(T t);

    protected abstract K getKey(ProducerContext producerContext);

    protected MultiplexProducer(Producer<T> inputProducer) {
        this.mInputProducer = inputProducer;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<T> consumer, ProducerContext context) {
        boolean createdNewMultiplexer;
        MultiplexProducer<K, T>.Multiplexer multiplexer;
        K key = getKey(context);
        do {
            createdNewMultiplexer = false;
            synchronized (this) {
                multiplexer = getExistingMultiplexer(key);
                if (multiplexer == null) {
                    multiplexer = createAndPutNewMultiplexer(key);
                    createdNewMultiplexer = true;
                }
            }
        } while (!multiplexer.addNewConsumer(consumer, context));
        if (!createdNewMultiplexer) {
            return;
        }
        multiplexer.startInputProducerIfHasAttachedConsumers();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized MultiplexProducer<K, T>.Multiplexer getExistingMultiplexer(K key) {
        return this.mMultiplexers.get(key);
    }

    private synchronized MultiplexProducer<K, T>.Multiplexer createAndPutNewMultiplexer(K key) {
        MultiplexProducer<K, T>.Multiplexer multiplexer;
        multiplexer = new Multiplexer(key);
        this.mMultiplexers.put(key, multiplexer);
        return multiplexer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void removeMultiplexer(K key, MultiplexProducer<K, T>.Multiplexer multiplexer) {
        if (this.mMultiplexers.get(key) == multiplexer) {
            this.mMultiplexers.remove(key);
        }
    }

    class Multiplexer {
        private final CopyOnWriteArraySet<Pair<Consumer<T>, ProducerContext>> mConsumerContextPairs = Sets.newCopyOnWriteArraySet();
        private MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer mForwardingConsumer;
        private final K mKey;
        private T mLastIntermediateResult;
        private float mLastProgress;
        private BaseProducerContext mMultiplexProducerContext;

        public Multiplexer(K key) {
            this.mKey = key;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public boolean addNewConsumer(Consumer<T> consumer, ProducerContext producerContext) {
            Pair<Consumer<T>, ProducerContext> consumerContextPair = Pair.create(consumer, producerContext);
            synchronized (this) {
                if (MultiplexProducer.this.getExistingMultiplexer(this.mKey) != this) {
                    return false;
                }
                this.mConsumerContextPairs.add(consumerContextPair);
                List<ProducerContextCallbacks> prefetchCallbacks = updateIsPrefetch();
                List<ProducerContextCallbacks> priorityCallbacks = updatePriority();
                List<ProducerContextCallbacks> intermediateResultsCallbacks = updateIsIntermediateResultExpected();
                Closeable closeableCloneOrNull = this.mLastIntermediateResult;
                float lastProgress = this.mLastProgress;
                BaseProducerContext.callOnIsPrefetchChanged(prefetchCallbacks);
                BaseProducerContext.callOnPriorityChanged(priorityCallbacks);
                BaseProducerContext.callOnIsIntermediateResultExpectedChanged(intermediateResultsCallbacks);
                synchronized (consumerContextPair) {
                    synchronized (this) {
                        if (closeableCloneOrNull != this.mLastIntermediateResult) {
                            closeableCloneOrNull = null;
                        } else if (closeableCloneOrNull != null) {
                            closeableCloneOrNull = MultiplexProducer.this.cloneOrNull(closeableCloneOrNull);
                        }
                    }
                    if (closeableCloneOrNull != null) {
                        if (lastProgress > 0.0f) {
                            consumer.onProgressUpdate(lastProgress);
                        }
                        consumer.onNewResult(closeableCloneOrNull, false);
                        closeSafely(closeableCloneOrNull);
                    }
                }
                addCallbacks(consumerContextPair, producerContext);
                return true;
            }
        }

        private void addCallbacks(final Pair<Consumer<T>, ProducerContext> consumerContextPair, ProducerContext producerContext) {
            producerContext.addCallbacks(new BaseProducerContextCallbacks() { // from class: com.facebook.imagepipeline.producers.MultiplexProducer.Multiplexer.1
                @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
                public void onCancellationRequested() {
                    boolean pairWasRemoved;
                    BaseProducerContext contextToCancel = null;
                    List<ProducerContextCallbacks> isPrefetchCallbacks = null;
                    List<ProducerContextCallbacks> priorityCallbacks = null;
                    List<ProducerContextCallbacks> isIntermediateResultExpectedCallbacks = null;
                    synchronized (Multiplexer.this) {
                        pairWasRemoved = Multiplexer.this.mConsumerContextPairs.remove(consumerContextPair);
                        if (pairWasRemoved) {
                            if (Multiplexer.this.mConsumerContextPairs.isEmpty()) {
                                contextToCancel = Multiplexer.this.mMultiplexProducerContext;
                            } else {
                                isPrefetchCallbacks = Multiplexer.this.updateIsPrefetch();
                                priorityCallbacks = Multiplexer.this.updatePriority();
                                isIntermediateResultExpectedCallbacks = Multiplexer.this.updateIsIntermediateResultExpected();
                            }
                        }
                    }
                    BaseProducerContext.callOnIsPrefetchChanged(isPrefetchCallbacks);
                    BaseProducerContext.callOnPriorityChanged(priorityCallbacks);
                    BaseProducerContext.callOnIsIntermediateResultExpectedChanged(isIntermediateResultExpectedCallbacks);
                    if (contextToCancel != null) {
                        contextToCancel.cancel();
                    }
                    if (pairWasRemoved) {
                        ((Consumer) consumerContextPair.first).onCancellation();
                    }
                }

                @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
                public void onIsPrefetchChanged() {
                    BaseProducerContext.callOnIsPrefetchChanged(Multiplexer.this.updateIsPrefetch());
                }

                @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
                public void onIsIntermediateResultExpectedChanged() {
                    BaseProducerContext.callOnIsIntermediateResultExpectedChanged(Multiplexer.this.updateIsIntermediateResultExpected());
                }

                @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
                public void onPriorityChanged() {
                    BaseProducerContext.callOnPriorityChanged(Multiplexer.this.updatePriority());
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void startInputProducerIfHasAttachedConsumers() {
            synchronized (this) {
                Preconditions.checkArgument(this.mMultiplexProducerContext == null);
                Preconditions.checkArgument(this.mForwardingConsumer == null);
                if (this.mConsumerContextPairs.isEmpty()) {
                    MultiplexProducer.this.removeMultiplexer(this.mKey, this);
                    return;
                }
                ProducerContext producerContext = (ProducerContext) this.mConsumerContextPairs.iterator().next().second;
                this.mMultiplexProducerContext = new BaseProducerContext(producerContext.getImageRequest(), producerContext.getId(), producerContext.getListener(), producerContext.getCallerContext(), producerContext.getLowestPermittedRequestLevel(), computeIsPrefetch(), computeIsIntermediateResultExpected(), computePriority());
                this.mForwardingConsumer = new ForwardingConsumer();
                BaseProducerContext multiplexProducerContext = this.mMultiplexProducerContext;
                MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer forwardingConsumer = this.mForwardingConsumer;
                MultiplexProducer.this.mInputProducer.produceResults(forwardingConsumer, multiplexProducerContext);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized List<ProducerContextCallbacks> updateIsPrefetch() {
            return this.mMultiplexProducerContext == null ? null : this.mMultiplexProducerContext.setIsPrefetchNoCallbacks(computeIsPrefetch());
        }

        private synchronized boolean computeIsPrefetch() {
            boolean z;
            for (Pair<Consumer<T>, ProducerContext> pair : this.mConsumerContextPairs) {
                if (!((ProducerContext) pair.second).isPrefetch()) {
                    z = false;
                }
            }
            z = true;
            return z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized List<ProducerContextCallbacks> updateIsIntermediateResultExpected() {
            List<ProducerContextCallbacks> isIntermediateResultExpectedNoCallbacks;
            if (this.mMultiplexProducerContext == null) {
                isIntermediateResultExpectedNoCallbacks = null;
            } else {
                isIntermediateResultExpectedNoCallbacks = this.mMultiplexProducerContext.setIsIntermediateResultExpectedNoCallbacks(computeIsIntermediateResultExpected());
            }
            return isIntermediateResultExpectedNoCallbacks;
        }

        private synchronized boolean computeIsIntermediateResultExpected() {
            boolean z;
            for (Pair<Consumer<T>, ProducerContext> pair : this.mConsumerContextPairs) {
                if (((ProducerContext) pair.second).isIntermediateResultExpected()) {
                    z = true;
                }
            }
            z = false;
            return z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized List<ProducerContextCallbacks> updatePriority() {
            return this.mMultiplexProducerContext == null ? null : this.mMultiplexProducerContext.setPriorityNoCallbacks(computePriority());
        }

        private synchronized Priority computePriority() {
            Priority priority;
            priority = Priority.LOW;
            for (Pair<Consumer<T>, ProducerContext> pair : this.mConsumerContextPairs) {
                priority = Priority.getHigherPriority(priority, ((ProducerContext) pair.second).getPriority());
            }
            return priority;
        }

        public void onFailure(MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer consumer, Throwable t) {
            synchronized (this) {
                if (this.mForwardingConsumer == consumer) {
                    this.mConsumerContextPairs.clear();
                    MultiplexProducer.this.removeMultiplexer(this.mKey, this);
                    closeSafely(this.mLastIntermediateResult);
                    this.mLastIntermediateResult = null;
                    for (Pair<Consumer<T>, ProducerContext> pair : this.mConsumerContextPairs) {
                        synchronized (pair) {
                            ((Consumer) pair.first).onFailure(t);
                        }
                    }
                }
            }
        }

        public void onNextResult(MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer forwardingConsumer, T t, boolean z) {
            synchronized (this) {
                if (this.mForwardingConsumer == forwardingConsumer) {
                    closeSafely(this.mLastIntermediateResult);
                    this.mLastIntermediateResult = null;
                    if (!z) {
                        this.mLastIntermediateResult = (T) MultiplexProducer.this.cloneOrNull(t);
                    } else {
                        this.mConsumerContextPairs.clear();
                        MultiplexProducer.this.removeMultiplexer(this.mKey, this);
                    }
                    for (Pair<Consumer<T>, ProducerContext> pair : this.mConsumerContextPairs) {
                        synchronized (pair) {
                            ((Consumer) pair.first).onNewResult(t, z);
                        }
                    }
                }
            }
        }

        public void onCancelled(MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer forwardingConsumer) {
            synchronized (this) {
                if (this.mForwardingConsumer == forwardingConsumer) {
                    this.mForwardingConsumer = null;
                    this.mMultiplexProducerContext = null;
                    closeSafely(this.mLastIntermediateResult);
                    this.mLastIntermediateResult = null;
                    startInputProducerIfHasAttachedConsumers();
                }
            }
        }

        public void onProgressUpdate(MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer forwardingConsumer, float progress) {
            synchronized (this) {
                if (this.mForwardingConsumer == forwardingConsumer) {
                    this.mLastProgress = progress;
                    for (Pair<Consumer<T>, ProducerContext> pair : this.mConsumerContextPairs) {
                        synchronized (pair) {
                            ((Consumer) pair.first).onProgressUpdate(progress);
                        }
                    }
                }
            }
        }

        private void closeSafely(Closeable obj) {
            if (obj != null) {
                try {
                    obj.close();
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        }

        private class ForwardingConsumer extends BaseConsumer<T> {
            private ForwardingConsumer() {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.imagepipeline.producers.BaseConsumer
            public void onNewResultImpl(T newResult, boolean isLast) {
                Multiplexer.this.onNextResult(this, newResult, isLast);
            }

            @Override // com.facebook.imagepipeline.producers.BaseConsumer
            protected void onFailureImpl(Throwable t) {
                Multiplexer.this.onFailure(this, t);
            }

            @Override // com.facebook.imagepipeline.producers.BaseConsumer
            protected void onCancellationImpl() {
                Multiplexer.this.onCancelled(this);
            }

            @Override // com.facebook.imagepipeline.producers.BaseConsumer
            protected void onProgressUpdateImpl(float progress) {
                Multiplexer.this.onProgressUpdate(this, progress);
            }
        }
    }
}
