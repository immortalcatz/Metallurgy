package shadow.mods.metallurgy.precious;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelBox;
import net.minecraft.src.ModelRenderer;


public class FM_ModelMintHead extends ModelBase
{

    private ModelRenderer Body;
    private ModelRenderer Shape2;
    private ModelRenderer Shape3;
    private ModelRenderer Shape4;
    private ModelRenderer Shape5;
    private ModelRenderer Bar0;
    private ModelRenderer Bar1;
    private ModelRenderer Bar2;
    private ModelRenderer Bar3;
    private ModelRenderer Head;
    
    public FM_ModelMintHead()
    {
        textureWidth = 64;
        textureHeight = 32;

        Body = new ModelRenderer(this, 0, 0);
        Body.addBox(0F, 11F, 0F, 16, 5, 16);
        Body.setTextureSize(64, 32);
        Body.mirror = true;
        
        Shape2 = new ModelRenderer(this, 12, 0);
        Shape2.addBox(0F, 10F, 0F, 16, 1, 4);
        Shape2.setTextureSize(64, 32);
        Shape2.mirror = true;
        setRotation(Shape2, 0F, 0F, 0F);
        Shape3 = new ModelRenderer(this, 24, 27);
        Shape3.addBox(0F, 10F, 12F, 16, 1, 4);
        Shape3.setTextureSize(64, 32);
        Shape3.mirror = true;
        setRotation(Shape3, 0F, 0F, 0F);
        Shape4 = new ModelRenderer(this, 0, 0);
        Shape4.addBox(0F, 10F, 4F, 6, 1, 8);
        Shape4.setTextureSize(64, 32);
        Shape4.mirror = true;
        setRotation(Shape4, 0F, 0F, 0F);
        Shape5 = new ModelRenderer(this, 18, 8);
        Shape5.addBox(10F, 10F, 4F, 6, 1, 8);
        Shape5.setTextureSize(64, 32);
        Shape5.mirror = true;
        setRotation(Shape5, 0F, 0F, 0F);
        Bar0 = new ModelRenderer(this, 0, 0);
        Bar0.addBox(1F, 0F, 1F, 1, 10, 1);
        Bar0.setTextureSize(64, 32);
        Bar0.mirror = true;
        setRotation(Bar0, 0F, 0F, 0F);
        Bar1 = new ModelRenderer(this, 0, 0);
        Bar1.addBox(14F, 0F, 1F, 1, 10, 1);
        Bar1.setTextureSize(64, 32);
        Bar1.mirror = true;
        setRotation(Bar1, 0F, 0F, 0F);
        Bar2 = new ModelRenderer(this, 0, 0);
        Bar2.addBox(1F, 0F, 14F, 1, 10, 1);
        Bar2.setTextureSize(64, 32);
        Bar2.mirror = true;
        setRotation(Bar2, 0F, 0F, 0F);
        Bar3 = new ModelRenderer(this, 0, 0);
        Bar3.addBox(14F, 0F, 14F, 1, 10, 1);
        Bar3.setTextureSize(64, 32);
        Bar3.mirror = true;
        setRotation(Bar3, 0F, 0F, 0F);
        Head = new ModelRenderer(this, 0, 11);
        Head.addBox(0F, 0F, 0F, 16, 4, 16);
        Head.setRotationPoint(0F, 1F, 0F);
        Head.setTextureSize(64, 32);
        Head.mirror = true;
        setRotation(Head, 0F, 0F, 0F);
    }

    /**
     * This method renders out all parts of the chest model.
     */
    public void renderAll()
    {
    	/*
        Body.render(0.0625F);
        Shape2.render(0.0625F);
        Shape3.render(0.0625F);
        Shape4.render(0.0625F);
        Shape5.render(0.0625F);
        Bar0.render(0.0625F);
        Bar1.render(0.0625F);
        Bar2.render(0.0625F);
        Bar3.render(0.0625F);
        */
        Head.render(0.0625F);
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
    }
}
