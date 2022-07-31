package picture.similarity;


import picture.similarity.prepare.getShape;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：li zhen
 * @description:
 * @date ：2022/7/29 14:50
 */
public class ShapeAlgorithm {

    public static void main(String[] args) throws IOException {
        getShape gh = new getShape();
        double[] shape = gh.shape("C:\\Users\\86181\\Desktop\\图形相似算法\\image\\image\\1.0.1-14.png");
        double[] shape1 = gh.shape("C:\\Users\\86181\\Desktop\\图形相似算法\\image\\image\\1.0.1-12.png");
        double[] shape2 = gh.shape("C:\\Users\\86181\\Desktop\\图形相似算法\\image\\image\\1.0.3-8.png");
        double[] shape3 = gh.shape("C:\\Users\\86181\\Desktop\\图形相似算法\\image\\image\\9.png");
        double v = cos_similar(shape, shape1);
        double v1 = cos_similar(shape, shape2);
        double v2 = cos_similar(shape, shape3);

        System.out.println(v);
        System.out.println(v1);
        System.out.println(v2);

        List<Double> doubles = minMaxScale(Arrays.asList(1D, 0.99D, 1D), 0D, 1D);
        System.out.println(Arrays.toString(doubles.toArray()));
    }

    /**
     * 采用余弦定理计算图片的形状相似度
     * @param daicha 待比较的图片特征值数组
     * @param kuzhi 参照图片特征值数组
     * @return 相似度，值越大说明越相似
     */
    public static double cos_similar(double[] daicha, double[] kuzhi) {
        double cosvalue = 1, fenzi = 0, fenmu1 = 0, fenmu2 = 0;
        for (int i = 0; i < kuzhi.length; i++) {
            fenzi += daicha[i] * kuzhi[i];
            fenmu1 += daicha[i] * daicha[i];
            fenmu2 += kuzhi[i] * kuzhi[i];
        }
        fenmu1 = Math.sqrt(fenmu1);
        fenmu2 = Math.sqrt(fenmu2);
        cosvalue = fenzi / (fenmu1 * fenmu2);

        return cosvalue;
    }

    /**
     * 数据归一化
     * @param datas 待归一化的数据
     * @param down 重新映射的数据下限
     * @param up 重新映射的数据上限
     * @return 重新映射后的数据
     */
    public static List<Double> minMaxScale(List<Double> datas, Double down, Double up) {
        Double min = Collections.min(datas);
        Double max = Collections.max(datas);
        double v = max - min;
        if (v == 0)
            v = up - down;

        double finalV = v;
        List<Double> result = datas.stream().map(data -> {
            double newData = (data - min) / finalV * (up - down) + down;
            return newData;
        }).collect(Collectors.toList());

        return result;

    }
}

        