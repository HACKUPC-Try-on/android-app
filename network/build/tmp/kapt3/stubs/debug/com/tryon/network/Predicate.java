package com.tryon.network;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u00032\u00020\u0004J)\u0010\u0005\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0006H&\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\nH&J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\fH&\u00a8\u0006\r"}, d2 = {"Lcom/tryon/network/Predicate;", "API", "T", "DTO", "", "endpoint", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "()Lkotlin/jvm/functions/Function2;", "mapper", "Lcom/tryon/network/Mapper;", "service", "Ljava/lang/Class;", "network_debug"})
public abstract interface Predicate<API extends java.lang.Object, T extends java.lang.Object, DTO extends java.lang.Object> {
    
    @org.jetbrains.annotations.NotNull
    public abstract java.lang.Class<API> service();
    
    @org.jetbrains.annotations.NotNull
    public abstract kotlin.jvm.functions.Function2<API, kotlin.coroutines.Continuation<? super DTO>, java.lang.Object> endpoint();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.tryon.network.Mapper<T, DTO> mapper();
}