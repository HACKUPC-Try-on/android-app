package com.tryon.network;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003J\u0015\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00028\u0001H&\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/tryon/network/Mapper;", "T", "DTO", "", "toDomainModel", "dto", "(Ljava/lang/Object;)Ljava/lang/Object;", "network_debug"})
public abstract interface Mapper<T extends java.lang.Object, DTO extends java.lang.Object> {
    
    public abstract T toDomainModel(DTO dto);
}