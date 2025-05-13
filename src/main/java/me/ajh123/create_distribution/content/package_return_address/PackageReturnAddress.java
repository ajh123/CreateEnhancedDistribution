package me.ajh123.create_distribution.content.package_return_address;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record PackageReturnAddress(String name, String address) {
    public static final Codec<PackageReturnAddress> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.STRING.fieldOf("name").forGetter(PackageReturnAddress::name),
            Codec.STRING.fieldOf("address").forGetter(PackageReturnAddress::address)
    ).apply(builder, PackageReturnAddress::new));
}
