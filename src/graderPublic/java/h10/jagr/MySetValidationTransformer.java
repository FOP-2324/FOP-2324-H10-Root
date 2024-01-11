package h10.jagr;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

import java.util.Set;

public class MySetValidationTransformer implements ClassTransformer {

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public void transform(ClassReader reader, ClassWriter writer) {
        if (reader.getClassName().equals("h10/MySet")) {
            reader.accept(new MySetVisitor(Opcodes.ASM9, writer), 0);
        } else {
            reader.accept(writer, 0);
        }
    }

    public static class MySetVisitor extends ClassVisitor {

        public MySetVisitor(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            return new ValidationVisitor(access, super.visitMethod(access, name, descriptor, signature, exceptions));
        }
    }

    private static class ValidationVisitor extends MethodVisitor {

        private static final Set<String> IGNORED_METHOD_CALLS = Set.of(
            "isOrdered",
            "isPairwiseDifferent"
        );

        public ValidationVisitor(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            if (IGNORED_METHOD_CALLS.contains(name)) {
                // Do nothing, ignore the method call
                return;
            }
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }
}
