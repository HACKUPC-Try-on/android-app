package com.tryon.network;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016J.\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00010\f2\u0018\u0010\r\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u000eH\u0084@\u00a2\u0006\u0002\u0010\u000fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/tryon/network/DataSource;", "API", "T", "DTO", "", "networkClient", "Lcom/tryon/network/NetworkClient;", "(Lcom/tryon/network/NetworkClient;)V", "errorHandler", "", "e", "retrieveData", "Lcom/tryon/network/DataResponse;", "predicate", "Lcom/tryon/network/Predicate;", "(Lcom/tryon/network/Predicate;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "network_debug"})
public abstract class DataSource<API extends java.lang.Object, T extends java.lang.Object, DTO extends java.lang.Object> {
    @org.jetbrains.annotations.NotNull
    private final com.tryon.network.NetworkClient networkClient = null;
    
    public DataSource(@org.jetbrains.annotations.NotNull
    com.tryon.network.NetworkClient networkClient) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    protected final java.lang.Object retrieveData(@org.jetbrains.annotations.NotNull
    com.tryon.network.Predicate<API, T, DTO> predicate, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.tryon.network.DataResponse<? extends T>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public java.lang.Throwable errorHandler(@org.jetbrains.annotations.NotNull
    java.lang.Throwable e) {
        return null;
    }
}