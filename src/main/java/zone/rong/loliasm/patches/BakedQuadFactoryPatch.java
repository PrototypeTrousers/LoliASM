package zone.rong.loliasm.patches;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import zone.rong.loliasm.bakedquad.BakedQuadClassFactory;

import java.util.ListIterator;

import static org.objectweb.asm.Opcodes.*;

/**
 * This class contains class writers for patched BakedQuadFactory
 * defineClass not called here, pass raw byte[] back to {@link zone.rong.loliasm.LoliTransformer#transform(String, String, byte[])}
 *
 * No optimizations for this patch, it is also not a whole class patch.
 * Just before the transformation, we predefine the classes via ASM in {@link BakedQuadClassFactory}
 *
 * The original placeholder is removed: {@link zone.rong.loliasm.bakedquad.BakedQuadFactory#create(int[], int, EnumFacing, TextureAtlasSprite, boolean, VertexFormat)}
 * Replaced with a create method that returns {@link net.minecraft.client.renderer.block.model.BakedQuad}
 *
 * Writing it like this also eliminates a synthetic inner class from being created: a.k.a the StateMap
 * It is what is created at compile time when there's an enum switch-case in the code.
 * Essentially a class with an int[] that holds ordinal values, we do the same (named 'pseudoSwitchMap')
 */
public final class BakedQuadFactoryPatch {

    public static byte[] patchCreateMethod(byte[] originalClass) {

        BakedQuadClassFactory.predefineBakedQuadClasses(); // Define classes prior to transforming!

        ClassReader reader = new ClassReader(originalClass);
        ClassNode node = new ClassNode();
        reader.accept(node, 0);

        ListIterator<MethodNode> methodIter = node.methods.listIterator();
        while (methodIter.hasNext()) {
            if (methodIter.next().name.equals("create")) {
                methodIter.remove();
            }
        }

        ClassWriter writer = new ClassWriter(0);

        node.accept(writer);

        MethodVisitor methodVisitor;

        methodVisitor = writer.visitMethod(ACC_PUBLIC | ACC_STATIC, "create", "([IILnet/minecraft/util/EnumFacing;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;ZLnet/minecraft/client/renderer/vertex/VertexFormat;)Lnet/minecraft/client/renderer/block/model/BakedQuad;", null, null);
        methodVisitor.visitCode();
        Label l0 = new Label();
        methodVisitor.visitLabel(l0);
        methodVisitor.visitLineNumber(181, l0);
        methodVisitor.visitFieldInsn(GETSTATIC, "zone/rong/loliasm/bakedquad/BakedQuadFactory", "pseudoSwitchMap", "[I");
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/util/EnumFacing", "ordinal", "()I", false);
        methodVisitor.visitInsn(IALOAD);
        Label l1 = new Label();
        Label l2 = new Label();
        Label l3 = new Label();
        Label l4 = new Label();
        Label l5 = new Label();
        Label l6 = new Label();
        Label l7 = new Label();
        methodVisitor.visitTableSwitchInsn(0, 5, l7, l1, l2, l3, l4, l5, l6);
        methodVisitor.visitLabel(l1);
        methodVisitor.visitLineNumber(183, l1);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ILOAD, 4);
        Label l8 = new Label();
        methodVisitor.visitJumpInsn(IFEQ, l8);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(ICONST_M1);
        Label l9 = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPNE, l9);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/DownDiffuseLightingNoTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/DownDiffuseLightingNoTint", "<init>", "([ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        Label l10 = new Label();
        methodVisitor.visitJumpInsn(GOTO, l10);
        methodVisitor.visitLabel(l9);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/DownDiffuseLightingTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/DownDiffuseLightingTint", "<init>", "([IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitJumpInsn(GOTO, l10);
        methodVisitor.visitLabel(l8);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(ICONST_M1);
        Label l11 = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPNE, l11);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/DownNoDiffuseLightingNoTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/DownNoDiffuseLightingNoTint", "<init>", "([ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitJumpInsn(GOTO, l10);
        methodVisitor.visitLabel(l11);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/DownNoDiffuseLightingTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/DownNoDiffuseLightingTint", "<init>", "([IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitLabel(l10);
        methodVisitor.visitFrame(F_SAME1, 0, null, 1, new Object[]{"net/minecraft/client/renderer/block/model/BakedQuad"});
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitLabel(l2);
        methodVisitor.visitLineNumber(185, l2);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ILOAD, 4);
        Label l12 = new Label();
        methodVisitor.visitJumpInsn(IFEQ, l12);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(ICONST_M1);
        Label l13 = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPNE, l13);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/UpDiffuseLightingNoTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/UpDiffuseLightingNoTint", "<init>", "([ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        Label l14 = new Label();
        methodVisitor.visitJumpInsn(GOTO, l14);
        methodVisitor.visitLabel(l13);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/UpDiffuseLightingTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/UpDiffuseLightingTint", "<init>", "([IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitJumpInsn(GOTO, l14);
        methodVisitor.visitLabel(l12);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(ICONST_M1);
        Label l15 = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPNE, l15);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/UpNoDiffuseLightingNoTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/UpNoDiffuseLightingNoTint", "<init>", "([ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitJumpInsn(GOTO, l14);
        methodVisitor.visitLabel(l15);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/UpNoDiffuseLightingTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/UpNoDiffuseLightingTint", "<init>", "([IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitLabel(l14);
        methodVisitor.visitFrame(F_SAME1, 0, null, 1, new Object[]{"net/minecraft/client/renderer/block/model/BakedQuad"});
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitLabel(l3);
        methodVisitor.visitLineNumber(187, l3);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ILOAD, 4);
        Label l16 = new Label();
        methodVisitor.visitJumpInsn(IFEQ, l16);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(ICONST_M1);
        Label l17 = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPNE, l17);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/NorthDiffuseLightingNoTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/NorthDiffuseLightingNoTint", "<init>", "([ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        Label l18 = new Label();
        methodVisitor.visitJumpInsn(GOTO, l18);
        methodVisitor.visitLabel(l17);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/NorthDiffuseLightingTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/NorthDiffuseLightingTint", "<init>", "([IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitJumpInsn(GOTO, l18);
        methodVisitor.visitLabel(l16);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(ICONST_M1);
        Label l19 = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPNE, l19);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/NorthNoDiffuseLightingNoTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/NorthNoDiffuseLightingNoTint", "<init>", "([ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitJumpInsn(GOTO, l18);
        methodVisitor.visitLabel(l19);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/NorthNoDiffuseLightingTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/NorthNoDiffuseLightingTint", "<init>", "([IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitLabel(l18);
        methodVisitor.visitFrame(F_SAME1, 0, null, 1, new Object[]{"net/minecraft/client/renderer/block/model/BakedQuad"});
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitLabel(l4);
        methodVisitor.visitLineNumber(189, l4);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ILOAD, 4);
        Label l20 = new Label();
        methodVisitor.visitJumpInsn(IFEQ, l20);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(ICONST_M1);
        Label l21 = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPNE, l21);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/SouthDiffuseLightingNoTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/SouthDiffuseLightingNoTint", "<init>", "([ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        Label l22 = new Label();
        methodVisitor.visitJumpInsn(GOTO, l22);
        methodVisitor.visitLabel(l21);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/SouthDiffuseLightingTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/SouthDiffuseLightingTint", "<init>", "([IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitJumpInsn(GOTO, l22);
        methodVisitor.visitLabel(l20);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(ICONST_M1);
        Label l23 = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPNE, l23);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/SouthNoDiffuseLightingNoTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/SouthNoDiffuseLightingNoTint", "<init>", "([ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitJumpInsn(GOTO, l22);
        methodVisitor.visitLabel(l23);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/SouthNoDiffuseLightingTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/SouthNoDiffuseLightingTint", "<init>", "([IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitLabel(l22);
        methodVisitor.visitFrame(F_SAME1, 0, null, 1, new Object[]{"net/minecraft/client/renderer/block/model/BakedQuad"});
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitLabel(l5);
        methodVisitor.visitLineNumber(191, l5);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ILOAD, 4);
        Label l24 = new Label();
        methodVisitor.visitJumpInsn(IFEQ, l24);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(ICONST_M1);
        Label l25 = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPNE, l25);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/WestDiffuseLightingNoTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/WestDiffuseLightingNoTint", "<init>", "([ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        Label l26 = new Label();
        methodVisitor.visitJumpInsn(GOTO, l26);
        methodVisitor.visitLabel(l25);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/WestDiffuseLightingTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/WestDiffuseLightingTint", "<init>", "([IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitJumpInsn(GOTO, l26);
        methodVisitor.visitLabel(l24);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(ICONST_M1);
        Label l27 = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPNE, l27);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/WestNoDiffuseLightingNoTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/WestNoDiffuseLightingNoTint", "<init>", "([ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitJumpInsn(GOTO, l26);
        methodVisitor.visitLabel(l27);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/WestNoDiffuseLightingTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/WestNoDiffuseLightingTint", "<init>", "([IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitLabel(l26);
        methodVisitor.visitFrame(F_SAME1, 0, null, 1, new Object[]{"net/minecraft/client/renderer/block/model/BakedQuad"});
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitLabel(l6);
        methodVisitor.visitLineNumber(193, l6);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ILOAD, 4);
        Label l28 = new Label();
        methodVisitor.visitJumpInsn(IFEQ, l28);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(ICONST_M1);
        Label l29 = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPNE, l29);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/EastDiffuseLightingNoTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/EastDiffuseLightingNoTint", "<init>", "([ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        Label l30 = new Label();
        methodVisitor.visitJumpInsn(GOTO, l30);
        methodVisitor.visitLabel(l29);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/EastDiffuseLightingTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/EastDiffuseLightingTint", "<init>", "([IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitJumpInsn(GOTO, l30);
        methodVisitor.visitLabel(l28);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(ICONST_M1);
        Label l31 = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPNE, l31);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/EastNoDiffuseLightingNoTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/EastNoDiffuseLightingNoTint", "<init>", "([ILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitJumpInsn(GOTO, l30);
        methodVisitor.visitLabel(l31);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitTypeInsn(NEW, "ifuckinglovelolis/bakedquads/EastNoDiffuseLightingTint");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "ifuckinglovelolis/bakedquads/EastNoDiffuseLightingTint", "<init>", "([IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/vertex/VertexFormat;)V", false);
        methodVisitor.visitLabel(l30);
        methodVisitor.visitFrame(F_SAME1, 0, null, 1, new Object[]{"net/minecraft/client/renderer/block/model/BakedQuad"});
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitLabel(l7);
        methodVisitor.visitLineNumber(195, l7);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitTypeInsn(NEW, "java/lang/IllegalStateException");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitLdcInsn("Lolis came and destroyed everything.");
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/IllegalStateException", "<init>", "(Ljava/lang/String;)V", false);
        methodVisitor.visitInsn(ATHROW);
        Label l32 = new Label();
        methodVisitor.visitLabel(l32);
        methodVisitor.visitLocalVariable("vertexDataIn", "[I", null, l0, l32, 0);
        methodVisitor.visitLocalVariable("tintIndexIn", "I", null, l0, l32, 1);
        methodVisitor.visitLocalVariable("faceIn", "Lnet/minecraft/util/EnumFacing;", null, l0, l32, 2);
        methodVisitor.visitLocalVariable("spriteIn", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", null, l0, l32, 3);
        methodVisitor.visitLocalVariable("applyDiffuseLighting", "Z", null, l0, l32, 4);
        methodVisitor.visitLocalVariable("format", "Lnet/minecraft/client/renderer/vertex/VertexFormat;", null, l0, l32, 5);
        methodVisitor.visitMaxs(6, 6);
        methodVisitor.visitEnd();

        writer.visitEnd();

        return writer.toByteArray();
    }

    private BakedQuadFactoryPatch() { }

}