package model.battlefield.map.atlas;

import java.util.List;

import com.jme3.texture.image.ImageRaster;

import geometry.collections.Map2D;

public class AtlasLayer extends Map2D<Double> {

	Map2D<Short> alphaMask;
	public ImageRaster mask;
	public double maskScale;
	
	public AtlasLayer(int xSize, int ySize) {
		super(xSize, ySize, 0d);
	}
	
	public double addAndReturnExcess(int x, int y, double toAdd){
		double excess = 0;
		double newVal = get(x, y) + toAdd;
		double maskVal = mask.getPixel((x)%(mask.getWidth()*(int)maskScale), (y)%(mask.getHeight()*(int)maskScale)).a;
		if(maskVal<1)
			newVal = Math.min(newVal, 255*maskVal);
		if (newVal > 255) {
			excess = newVal - 255;
			newVal = 255;
		}
		set(x, y, newVal);
		return excess;
	}
	
	public double withdrawAndReturnExcess(int x, int y, double toWithdraw) {
		double excess = 0;
		double newVal = get(x, y) - toWithdraw;
		if (newVal < 0) {
			excess = -newVal;
			newVal = 0;
		}
		set(x, y, newVal);
		return excess;
	}
	
	public void setMask(int width, int height, List<Short> data, int scale){
		alphaMask = new Map2D<>(width, height);
		for(int i = 0; i < width*height; i++)
			alphaMask.set(i, data.get(i));
		maskScale = scale;
	}
}
