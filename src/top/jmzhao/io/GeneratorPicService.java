package top.jmzhao.io;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class GeneratorPicService {

	public BufferedImage loadImageUrl(String imgUrl) {
		try {
			URL url = new URL(imgUrl);
			return ImageIO.read(url);
		} catch (IOException e) {
			return null;
		}
	}

	public BufferedImage loadLocalImage(String filePath) {
		try {
			return ImageIO.read(new File(filePath));
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * 将图片from画到to中的以(xPosition,yPosition)为起点的位置,宽为width,高为height
	 */
	public BufferedImage mergePic(BufferedImage from, BufferedImage to, int xPosition, int yPosition, int width, int height) {
		Graphics2D graphics2d = to.createGraphics();
		graphics2d.drawImage(from, xPosition, yPosition, width, height, null);
		graphics2d.dispose();
		return to;
	}

	/**
	 * 在图片上写字,高宽需新加参数
	 */
	public BufferedImage setFont(BufferedImage image, int size, Color color, String name) {
		int yPosition = 130; // 高度设置
		if (name.length() > 6) {
			name = name.substring(0, 6) + "...";
		}
		Graphics2D graphics2d = image.createGraphics();
		FontRenderContext context = graphics2d.getFontRenderContext();
		Font font = new Font("宋体", Font.BOLD, size);
		Rectangle2D bounds = font.getStringBounds(name, context);
		int xPosition = (int) (750 - (bounds.getWidth() + 160)); // 根据字的大小确定位置
		graphics2d.setPaint(color);
		graphics2d.setFont(font);
		graphics2d.drawString(name, xPosition, yPosition);
		graphics2d.dispose();
		return image;
	}

	/**
	 * 在图片上写字，提供x,y位置参数
	 * @param image 背景图
	 * @param xPosition 待写文字的x位置
	 * @param yPosition 待写文字的y位置
	 * @param size 文字大小
	 * @param color 文字颜色
	 * @param name 文字内容
	 * @return 返回修改后的图片
	 */
	public BufferedImage setFont(BufferedImage image, int xPosition, int yPosition, int size, Color color, String name) {
		if (xPosition <= 0 || yPosition <= 0)
			throw new IllegalArgumentException("文字x,y位置必须大于0");
		if (image.getHeight() < yPosition || image.getWidth() < xPosition)
			throw new IllegalArgumentException("文字y位置大于图片高度或文字x位置大于图片宽度");
		Graphics2D graphics2d = image.createGraphics();
		Font font = new Font("宋体", Font.BOLD, size);
		graphics2d.setPaint(color);
		graphics2d.setFont(font);
		graphics2d.drawString(name, xPosition, yPosition);
		graphics2d.dispose();
		return image;
	}

	/**
	 * 生成高质量的缩略图
	 */
	public BufferedImage zoomImageScale(BufferedImage image, int newWidth, int newHeight) {
		int type = image.getColorModel().getTransparency();
		int width = image.getWidth();
		int height = image.getHeight();

		RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		BufferedImage newImage = new BufferedImage(newWidth, newHeight, type);
		Graphics2D graphics2d = newImage.createGraphics();
		graphics2d.setRenderingHints(renderingHints);
		graphics2d.drawImage(image, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
		graphics2d.dispose();
		return image;
	}

	/**
	 * 正方形图片变成圆形,其实四角还在的
	 */
	public BufferedImage convertCircular(BufferedImage bi1) {
		// 透明底的图片
		BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1.getHeight());
		Graphics2D g2 = bi2.createGraphics();
		g2.setClip(shape);
		g2.drawImage(bi1, 0, 0, null);
		g2.dispose();
		return bi2;
	}
	
	public void createBackGroupPic() {
		int width = 500;
		int height = 625;
		String s = "你好";

		File file = new File("E:/Pic/img.jpg");
		Font font = new Font("Serif", Font.BOLD, 30);
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = bi.createGraphics();
		graphics2d.setBackground(Color.WHITE);
		graphics2d.clearRect(0, 0, width, height);
		graphics2d.setPaint(Color.RED);

		FontRenderContext context = graphics2d.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(s, context);
		System.out.println("width-->" + bounds.getWidth());
		System.out.println("height-->" + bounds.getHeight());
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = -bounds.getY();
		double baseY = y + ascent;

		graphics2d.drawString(s, (int) x, (int) baseY);
		try {
			ImageIO.write(bi, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		GeneratorPicService pic = new GeneratorPicService();
		// BufferedImage from =
		// pic.loadImageUrl("http://i04.pictn.sogoucdn.com/67e4cf84256ff725");
		// BufferedImage to =
		// pic.loadImageUrl("http://img03.sogoucdn.com/app/a/100520093/c88b7183d9b7bfd0-c4c6d143bfc4d850-62d88b0976a4111f536a6070cc32aadf.jpg");
		// BufferedImage newToPic = pic.mergePic(from, to);
		// String targetFilePath = "D:/Pic/pic/10269494_100007265124_2.jpg";
		// //需要新建张随意图片用来保存
		// try {
		// ImageIO.write(newToPic, targetFilePath.split("\\.")[1], new
		// File(targetFilePath));
		// } catch (IOException e) {
		// System.out.println("导出图片出错-->" + e);
		// }

		// pic.createBackGroupPic();

		// BufferedImage from =
		// pic.loadImageUrl("http://wx.qlogo.cn/mmopen/2MUiaX4SHzq0WqsxRz1qXc9I71uWs8oIUD0OrIxOzpSgOGsRgib0Yk8ml0w69qxocqLwJ5v6x7S3vYmtWXxziaXyQ/0");
		// 缩小图片
		// from = pic.createThumbnailImg(from);

		BufferedImage from = pic.loadLocalImage("F:\\测试素材\\图片\\背景图1.jpg");

//		BufferedImage to = pic.loadLocalImage("E:/Pic/微信图片_20171128180610.png");
//		URL url = ClassLoader.getSystemResource("image/promotion_basemap.jpeg");
//		File localFile = new File("F:\\输出目录\\test.jpg");
//		BufferedImage to = null;
//		try {
//			to = ImageIO.read(localFile);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		BufferedImage newToPic = pic.mergePic(from, to, 347, 916, 200, 200);
		Color color = new Color(208, 52, 52);
		BufferedImage newToPic = pic.setFont(from, 28, color, "zjm123243434");

//		BufferedImage customerPic = pic.loadImageUrl("http://wx.qlogo.cn/mmopen/2MUiaX4SHzq0WqsxRz1qXc9I71uWs8oIUD0OrIxOzpSgOGsRgib0Yk8ml0w69qxocqLwJ5v6x7S3vYmtWXxziaXyQ/0");
//		customerPic = pic.zoomImageScale(customerPic, 100, 100);
//		customerPic = pic.convertCircular(customerPic);

//		BufferedImage donePic = pic.mergePic(customerPic, newToPic, 620, 80, 100, 100);

		File file = new File("F:\\输出目录\\test.jpg");
		try {
			ImageIO.write(newToPic, "jpeg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("success");
	}
}
