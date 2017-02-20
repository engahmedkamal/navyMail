package navymail.Helpers.Master;

public class ImageSort implements Comparable<ImageSort> {
	int num;
	public String img;

	public ImageSort(String img) {
		this.img = img;
		num = getNum(img);
	}

	static int getNum(String s) {
		int n = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) >= '0' && s.charAt(i) <= '9')
				n = n * 10 + s.charAt(i) - '0';
		}
		return n;
	}

	@Override
	public int compareTo(ImageSort arg0) {
		return num - arg0.num;
	}

}
