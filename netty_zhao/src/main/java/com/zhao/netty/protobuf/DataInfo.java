// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Student.proto

package com.zhao.netty.protobuf;

public final class DataInfo {
    private DataInfo() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions(
                (com.google.protobuf.ExtensionRegistryLite) registry);
    }

    public interface StudentOrBuilder extends
            // @@protoc_insertion_point(interface_extends:com.luban.protobuf.Student)
            com.google.protobuf.MessageOrBuilder {

        /**
         * <code>required string name = 1;</code>
         */
        boolean hasName();

        /**
         * <code>required string name = 1;</code>
         */
        String getName();

        /**
         * <code>required string name = 1;</code>
         */
        com.google.protobuf.ByteString
        getNameBytes();

        /**
         * <code>optional int32 age = 2;</code>
         */
        boolean hasAge();

        /**
         * <code>optional int32 age = 2;</code>
         */
        int getAge();

        /**
         * <code>optional string address = 3;</code>
         */
        boolean hasAddress();

        /**
         * <code>optional string address = 3;</code>
         */
        String getAddress();

        /**
         * <code>optional string address = 3;</code>
         */
        com.google.protobuf.ByteString
        getAddressBytes();
    }

    /**
     * Protobuf type {@code com.luban.protobuf.Student}
     */
    public static final class Student extends
            com.google.protobuf.GeneratedMessageV3 implements
            // @@protoc_insertion_point(message_implements:com.luban.protobuf.Student)
            StudentOrBuilder {
        private static final long serialVersionUID = 0L;

        // Use Student.newBuilder() to construct.
        private Student(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Student() {
            name_ = "";
            address_ = "";
        }

        @Override
        @SuppressWarnings({"unused"})
        protected Object newInstance(
                UnusedPrivateParameter unused) {
            return new Student();
        }

        @Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }

        private Student(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10: {
                            com.google.protobuf.ByteString bs = input.readBytes();
                            bitField0_ |= 0x00000001;
                            name_ = bs;
                            break;
                        }
                        case 16: {
                            bitField0_ |= 0x00000002;
                            age_ = input.readInt32();
                            break;
                        }
                        case 26: {
                            com.google.protobuf.ByteString bs = input.readBytes();
                            bitField0_ |= 0x00000004;
                            address_ = bs;
                            break;
                        }
                        default: {
                            if (!parseUnknownField(
                                    input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return DataInfo.internal_static_com_luban_protobuf_Student_descriptor;
        }

        @Override
        protected FieldAccessorTable
        internalGetFieldAccessorTable() {
            return DataInfo.internal_static_com_luban_protobuf_Student_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            Student.class, Builder.class);
        }

        private int bitField0_;
        public static final int NAME_FIELD_NUMBER = 1;
        private volatile Object name_;

        /**
         * <code>required string name = 1;</code>
         */
        public boolean hasName() {
            return ((bitField0_ & 0x00000001) != 0);
        }

        /**
         * <code>required string name = 1;</code>
         */
        public String getName() {
            Object ref = name_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    name_ = s;
                }
                return s;
            }
        }

        /**
         * <code>required string name = 1;</code>
         */
        public com.google.protobuf.ByteString
        getNameBytes() {
            Object ref = name_;
            if (ref instanceof String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (String) ref);
                name_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        public static final int AGE_FIELD_NUMBER = 2;
        private int age_;

        /**
         * <code>optional int32 age = 2;</code>
         */
        public boolean hasAge() {
            return ((bitField0_ & 0x00000002) != 0);
        }

        /**
         * <code>optional int32 age = 2;</code>
         */
        public int getAge() {
            return age_;
        }

        public static final int ADDRESS_FIELD_NUMBER = 3;
        private volatile Object address_;

        /**
         * <code>optional string address = 3;</code>
         */
        public boolean hasAddress() {
            return ((bitField0_ & 0x00000004) != 0);
        }

        /**
         * <code>optional string address = 3;</code>
         */
        public String getAddress() {
            Object ref = address_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    address_ = s;
                }
                return s;
            }
        }

        /**
         * <code>optional string address = 3;</code>
         */
        public com.google.protobuf.ByteString
        getAddressBytes() {
            Object ref = address_;
            if (ref instanceof String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (String) ref);
                address_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        private byte memoizedIsInitialized = -1;

        @Override
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1) return true;
            if (isInitialized == 0) return false;

            if (!hasName()) {
                memoizedIsInitialized = 0;
                return false;
            }
            memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            if (((bitField0_ & 0x00000001) != 0)) {
                com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
            }
            if (((bitField0_ & 0x00000002) != 0)) {
                output.writeInt32(2, age_);
            }
            if (((bitField0_ & 0x00000004) != 0)) {
                com.google.protobuf.GeneratedMessageV3.writeString(output, 3, address_);
            }
            unknownFields.writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1) return size;

            size = 0;
            if (((bitField0_ & 0x00000001) != 0)) {
                size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
            }
            if (((bitField0_ & 0x00000002) != 0)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeInt32Size(2, age_);
            }
            if (((bitField0_ & 0x00000004) != 0)) {
                size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, address_);
            }
            size += unknownFields.getSerializedSize();
            memoizedSize = size;
            return size;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Student)) {
                return super.equals(obj);
            }
            Student other = (Student) obj;

            if (hasName() != other.hasName()) return false;
            if (hasName()) {
                if (!getName()
                        .equals(other.getName())) return false;
            }
            if (hasAge() != other.hasAge()) return false;
            if (hasAge()) {
                if (getAge()
                        != other.getAge()) return false;
            }
            if (hasAddress() != other.hasAddress()) return false;
            if (hasAddress()) {
                if (!getAddress()
                        .equals(other.getAddress())) return false;
            }
            if (!unknownFields.equals(other.unknownFields)) return false;
            return true;
        }

        @Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptor().hashCode();
            if (hasName()) {
                hash = (37 * hash) + NAME_FIELD_NUMBER;
                hash = (53 * hash) + getName().hashCode();
            }
            if (hasAge()) {
                hash = (37 * hash) + AGE_FIELD_NUMBER;
                hash = (53 * hash) + getAge();
            }
            if (hasAddress()) {
                hash = (37 * hash) + ADDRESS_FIELD_NUMBER;
                hash = (53 * hash) + getAddress().hashCode();
            }
            hash = (29 * hash) + unknownFields.hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static Student parseFrom(
                java.nio.ByteBuffer data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Student parseFrom(
                java.nio.ByteBuffer data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Student parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Student parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Student parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Student parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Student parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static Student parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Student parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input);
        }

        public static Student parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Student parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static Student parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Student prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE
                    ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(
                BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        /**
         * Protobuf type {@code com.luban.protobuf.Student}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:com.luban.protobuf.Student)
                StudentOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return DataInfo.internal_static_com_luban_protobuf_Student_descriptor;
            }

            @Override
            protected FieldAccessorTable
            internalGetFieldAccessorTable() {
                return DataInfo.internal_static_com_luban_protobuf_Student_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                Student.class, Builder.class);
            }

            // Construct using com.luban.netty.protobuf.DataInfo.Student.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessageV3
                        .alwaysUseFieldBuilders) {
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                name_ = "";
                bitField0_ = (bitField0_ & ~0x00000001);
                age_ = 0;
                bitField0_ = (bitField0_ & ~0x00000002);
                address_ = "";
                bitField0_ = (bitField0_ & ~0x00000004);
                return this;
            }

            @Override
            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return DataInfo.internal_static_com_luban_protobuf_Student_descriptor;
            }

            @Override
            public Student getDefaultInstanceForType() {
                return Student.getDefaultInstance();
            }

            @Override
            public Student build() {
                Student result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Student buildPartial() {
                Student result = new Student(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) != 0)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.name_ = name_;
                if (((from_bitField0_ & 0x00000002) != 0)) {
                    result.age_ = age_;
                    to_bitField0_ |= 0x00000002;
                }
                if (((from_bitField0_ & 0x00000004) != 0)) {
                    to_bitField0_ |= 0x00000004;
                }
                result.address_ = address_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            @Override
            public Builder clone() {
                return super.clone();
            }

            @Override
            public Builder setField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return super.setField(field, value);
            }

            @Override
            public Builder clearField(
                    com.google.protobuf.Descriptors.FieldDescriptor field) {
                return super.clearField(field);
            }

            @Override
            public Builder clearOneof(
                    com.google.protobuf.Descriptors.OneofDescriptor oneof) {
                return super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    int index, Object value) {
                return super.setRepeatedField(field, index, value);
            }

            @Override
            public Builder addRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return super.addRepeatedField(field, value);
            }

            @Override
            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof Student) {
                    return mergeFrom((Student) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(Student other) {
                if (other == Student.getDefaultInstance()) return this;
                if (other.hasName()) {
                    bitField0_ |= 0x00000001;
                    name_ = other.name_;
                    onChanged();
                }
                if (other.hasAge()) {
                    setAge(other.getAge());
                }
                if (other.hasAddress()) {
                    bitField0_ |= 0x00000004;
                    address_ = other.address_;
                    onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                if (!hasName()) {
                    return false;
                }
                return true;
            }

            @Override
            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                Student parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (Student) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int bitField0_;

            private Object name_ = "";

            /**
             * <code>required string name = 1;</code>
             */
            public boolean hasName() {
                return ((bitField0_ & 0x00000001) != 0);
            }

            /**
             * <code>required string name = 1;</code>
             */
            public String getName() {
                Object ref = name_;
                if (!(ref instanceof String)) {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        name_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>required string name = 1;</code>
             */
            public com.google.protobuf.ByteString
            getNameBytes() {
                Object ref = name_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (String) ref);
                    name_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            /**
             * <code>required string name = 1;</code>
             */
            public Builder setName(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                name_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required string name = 1;</code>
             */
            public Builder clearName() {
                bitField0_ = (bitField0_ & ~0x00000001);
                name_ = getDefaultInstance().getName();
                onChanged();
                return this;
            }

            /**
             * <code>required string name = 1;</code>
             */
            public Builder setNameBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                name_ = value;
                onChanged();
                return this;
            }

            private int age_;

            /**
             * <code>optional int32 age = 2;</code>
             */
            public boolean hasAge() {
                return ((bitField0_ & 0x00000002) != 0);
            }

            /**
             * <code>optional int32 age = 2;</code>
             */
            public int getAge() {
                return age_;
            }

            /**
             * <code>optional int32 age = 2;</code>
             */
            public Builder setAge(int value) {
                bitField0_ |= 0x00000002;
                age_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>optional int32 age = 2;</code>
             */
            public Builder clearAge() {
                bitField0_ = (bitField0_ & ~0x00000002);
                age_ = 0;
                onChanged();
                return this;
            }

            private Object address_ = "";

            /**
             * <code>optional string address = 3;</code>
             */
            public boolean hasAddress() {
                return ((bitField0_ & 0x00000004) != 0);
            }

            /**
             * <code>optional string address = 3;</code>
             */
            public String getAddress() {
                Object ref = address_;
                if (!(ref instanceof String)) {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        address_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>optional string address = 3;</code>
             */
            public com.google.protobuf.ByteString
            getAddressBytes() {
                Object ref = address_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (String) ref);
                    address_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            /**
             * <code>optional string address = 3;</code>
             */
            public Builder setAddress(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000004;
                address_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>optional string address = 3;</code>
             */
            public Builder clearAddress() {
                bitField0_ = (bitField0_ & ~0x00000004);
                address_ = getDefaultInstance().getAddress();
                onChanged();
                return this;
            }

            /**
             * <code>optional string address = 3;</code>
             */
            public Builder setAddressBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000004;
                address_ = value;
                onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.mergeUnknownFields(unknownFields);
            }


            // @@protoc_insertion_point(builder_scope:com.luban.protobuf.Student)
        }

        // @@protoc_insertion_point(class_scope:com.luban.protobuf.Student)
        private static final Student DEFAULT_INSTANCE;

        static {
            DEFAULT_INSTANCE = new Student();
        }

        public static Student getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        @Deprecated
        public static final com.google.protobuf.Parser<Student>
                PARSER = new com.google.protobuf.AbstractParser<Student>() {
            @Override
            public Student parsePartialFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return new Student(input, extensionRegistry);
            }
        };

        public static com.google.protobuf.Parser<Student> parser() {
            return PARSER;
        }

        @Override
        public com.google.protobuf.Parser<Student> getParserForType() {
            return PARSER;
        }

        @Override
        public Student getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

    }

    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_com_luban_protobuf_Student_descriptor;
    private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_com_luban_protobuf_Student_fieldAccessorTable;

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }

    private static com.google.protobuf.Descriptors.FileDescriptor
            descriptor;

    static {
        String[] descriptorData = {
                "\n\rStudent.proto\022\022com.luban.protobuf\"5\n\007S" +
                        "tudent\022\014\n\004name\030\001 \002(\t\022\013\n\003age\030\002 \001(\005\022\017\n\007add" +
                        "ress\030\003 \001(\tB$\n\030com.luban.netty.protobufB\010" +
                        "DataInfo"
        };
        descriptor = com.google.protobuf.Descriptors.FileDescriptor
                .internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[]{
                        });
        internal_static_com_luban_protobuf_Student_descriptor =
                getDescriptor().getMessageTypes().get(0);
        internal_static_com_luban_protobuf_Student_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_com_luban_protobuf_Student_descriptor,
                new String[]{"Name", "Age", "Address",});
    }

    // @@protoc_insertion_point(outer_class_scope)
}