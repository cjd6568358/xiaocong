package com.facebook.react.uimanager;

import com.facebook.infer.annotation.Assertions;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MatrixMathHelper {

    public static class MatrixDecompositionContext {
        double[] perspective = new double[4];
        double[] quaternion = new double[4];
        double[] scale = new double[3];
        double[] skew = new double[3];
        double[] translation = new double[3];
        double[] rotationDegrees = new double[3];
    }

    private static boolean isZero(double d) {
        return !Double.isNaN(d) && Math.abs(d) < 1.0E-5d;
    }

    public static void multiplyInto(double[] out, double[] a, double[] b) {
        double a00 = a[0];
        double a01 = a[1];
        double a02 = a[2];
        double a03 = a[3];
        double a10 = a[4];
        double a11 = a[5];
        double a12 = a[6];
        double a13 = a[7];
        double a20 = a[8];
        double a21 = a[9];
        double a22 = a[10];
        double a23 = a[11];
        double a30 = a[12];
        double a31 = a[13];
        double a32 = a[14];
        double a33 = a[15];
        double b0 = b[0];
        double b1 = b[1];
        double b2 = b[2];
        double b3 = b[3];
        out[0] = (b0 * a00) + (b1 * a10) + (b2 * a20) + (b3 * a30);
        out[1] = (b0 * a01) + (b1 * a11) + (b2 * a21) + (b3 * a31);
        out[2] = (b0 * a02) + (b1 * a12) + (b2 * a22) + (b3 * a32);
        out[3] = (b0 * a03) + (b1 * a13) + (b2 * a23) + (b3 * a33);
        double b4 = b[4];
        double b5 = b[5];
        double b6 = b[6];
        double b7 = b[7];
        out[4] = (b4 * a00) + (b5 * a10) + (b6 * a20) + (b7 * a30);
        out[5] = (b4 * a01) + (b5 * a11) + (b6 * a21) + (b7 * a31);
        out[6] = (b4 * a02) + (b5 * a12) + (b6 * a22) + (b7 * a32);
        out[7] = (b4 * a03) + (b5 * a13) + (b6 * a23) + (b7 * a33);
        double b8 = b[8];
        double b9 = b[9];
        double b10 = b[10];
        double b11 = b[11];
        out[8] = (b8 * a00) + (b9 * a10) + (b10 * a20) + (b11 * a30);
        out[9] = (b8 * a01) + (b9 * a11) + (b10 * a21) + (b11 * a31);
        out[10] = (b8 * a02) + (b9 * a12) + (b10 * a22) + (b11 * a32);
        out[11] = (b8 * a03) + (b9 * a13) + (b10 * a23) + (b11 * a33);
        double b12 = b[12];
        double b13 = b[13];
        double b14 = b[14];
        double b15 = b[15];
        out[12] = (b12 * a00) + (b13 * a10) + (b14 * a20) + (b15 * a30);
        out[13] = (b12 * a01) + (b13 * a11) + (b14 * a21) + (b15 * a31);
        out[14] = (b12 * a02) + (b13 * a12) + (b14 * a22) + (b15 * a32);
        out[15] = (b12 * a03) + (b13 * a13) + (b14 * a23) + (b15 * a33);
    }

    public static void decomposeMatrix(double[] transformMatrix, MatrixDecompositionContext ctx) {
        Assertions.assertCondition(transformMatrix.length == 16);
        double[] perspective = ctx.perspective;
        double[] quaternion = ctx.quaternion;
        double[] scale = ctx.scale;
        double[] skew = ctx.skew;
        double[] translation = ctx.translation;
        double[] rotationDegrees = ctx.rotationDegrees;
        if (!isZero(transformMatrix[15])) {
            double[][] matrix = (double[][]) Array.newInstance((Class<?>) Double.TYPE, 4, 4);
            double[] perspectiveMatrix = new double[16];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    double value = transformMatrix[(i * 4) + j] / transformMatrix[15];
                    matrix[i][j] = value;
                    int i2 = (i * 4) + j;
                    if (j == 3) {
                        value = 0.0d;
                    }
                    perspectiveMatrix[i2] = value;
                }
            }
            perspectiveMatrix[15] = 1.0d;
            if (!isZero(determinant(perspectiveMatrix))) {
                if (!isZero(matrix[0][3]) || !isZero(matrix[1][3]) || !isZero(matrix[2][3])) {
                    double[] rightHandSide = {matrix[0][3], matrix[1][3], matrix[2][3], matrix[3][3]};
                    double[] inversePerspectiveMatrix = inverse(perspectiveMatrix);
                    double[] transposedInversePerspectiveMatrix = transpose(inversePerspectiveMatrix);
                    multiplyVectorByMatrix(rightHandSide, transposedInversePerspectiveMatrix, perspective);
                } else {
                    perspective[2] = 0.0d;
                    perspective[1] = 0.0d;
                    perspective[0] = 0.0d;
                    perspective[3] = 1.0d;
                }
                for (int i3 = 0; i3 < 3; i3++) {
                    translation[i3] = matrix[3][i3];
                }
                double[][] row = (double[][]) Array.newInstance((Class<?>) Double.TYPE, 3, 3);
                for (int i4 = 0; i4 < 3; i4++) {
                    row[i4][0] = matrix[i4][0];
                    row[i4][1] = matrix[i4][1];
                    row[i4][2] = matrix[i4][2];
                }
                scale[0] = v3Length(row[0]);
                row[0] = v3Normalize(row[0], scale[0]);
                skew[0] = v3Dot(row[0], row[1]);
                row[1] = v3Combine(row[1], row[0], 1.0d, -skew[0]);
                skew[0] = v3Dot(row[0], row[1]);
                row[1] = v3Combine(row[1], row[0], 1.0d, -skew[0]);
                scale[1] = v3Length(row[1]);
                row[1] = v3Normalize(row[1], scale[1]);
                skew[0] = skew[0] / scale[1];
                skew[1] = v3Dot(row[0], row[2]);
                row[2] = v3Combine(row[2], row[0], 1.0d, -skew[1]);
                skew[2] = v3Dot(row[1], row[2]);
                row[2] = v3Combine(row[2], row[1], 1.0d, -skew[2]);
                scale[2] = v3Length(row[2]);
                row[2] = v3Normalize(row[2], scale[2]);
                skew[1] = skew[1] / scale[2];
                skew[2] = skew[2] / scale[2];
                double[] pdum3 = v3Cross(row[1], row[2]);
                if (v3Dot(row[0], pdum3) < 0.0d) {
                    for (int i5 = 0; i5 < 3; i5++) {
                        scale[i5] = scale[i5] * (-1.0d);
                        double[] dArr = row[i5];
                        dArr[0] = dArr[0] * (-1.0d);
                        double[] dArr2 = row[i5];
                        dArr2[1] = dArr2[1] * (-1.0d);
                        double[] dArr3 = row[i5];
                        dArr3[2] = dArr3[2] * (-1.0d);
                    }
                }
                quaternion[0] = 0.5d * Math.sqrt(Math.max(((1.0d + row[0][0]) - row[1][1]) - row[2][2], 0.0d));
                quaternion[1] = 0.5d * Math.sqrt(Math.max(((1.0d - row[0][0]) + row[1][1]) - row[2][2], 0.0d));
                quaternion[2] = 0.5d * Math.sqrt(Math.max(((1.0d - row[0][0]) - row[1][1]) + row[2][2], 0.0d));
                quaternion[3] = 0.5d * Math.sqrt(Math.max(1.0d + row[0][0] + row[1][1] + row[2][2], 0.0d));
                if (row[2][1] > row[1][2]) {
                    quaternion[0] = -quaternion[0];
                }
                if (row[0][2] > row[2][0]) {
                    quaternion[1] = -quaternion[1];
                }
                if (row[1][0] > row[0][1]) {
                    quaternion[2] = -quaternion[2];
                }
                if (quaternion[0] < 0.001d && quaternion[0] >= 0.0d && quaternion[1] < 0.001d && quaternion[1] >= 0.0d) {
                    rotationDegrees[1] = 0.0d;
                    rotationDegrees[0] = 0.0d;
                    rotationDegrees[2] = roundTo3Places((Math.atan2(row[0][1], row[0][0]) * 180.0d) / 3.141592653589793d);
                    return;
                }
                quaternionToDegreesXYZ(quaternion, rotationDegrees);
            }
        }
    }

    public static double determinant(double[] matrix) {
        double m00 = matrix[0];
        double m01 = matrix[1];
        double m02 = matrix[2];
        double m03 = matrix[3];
        double m10 = matrix[4];
        double m11 = matrix[5];
        double m12 = matrix[6];
        double m13 = matrix[7];
        double m20 = matrix[8];
        double m21 = matrix[9];
        double m22 = matrix[10];
        double m23 = matrix[11];
        double m30 = matrix[12];
        double m31 = matrix[13];
        double m32 = matrix[14];
        double m33 = matrix[15];
        return (((((((((((((((((((((((((m03 * m12) * m21) * m30) - (((m02 * m13) * m21) * m30)) - (((m03 * m11) * m22) * m30)) + (((m01 * m13) * m22) * m30)) + (((m02 * m11) * m23) * m30)) - (((m01 * m12) * m23) * m30)) - (((m03 * m12) * m20) * m31)) + (((m02 * m13) * m20) * m31)) + (((m03 * m10) * m22) * m31)) - (((m00 * m13) * m22) * m31)) - (((m02 * m10) * m23) * m31)) + (((m00 * m12) * m23) * m31)) + (((m03 * m11) * m20) * m32)) - (((m01 * m13) * m20) * m32)) - (((m03 * m10) * m21) * m32)) + (((m00 * m13) * m21) * m32)) + (((m01 * m10) * m23) * m32)) - (((m00 * m11) * m23) * m32)) - (((m02 * m11) * m20) * m33)) + (((m01 * m12) * m20) * m33)) + (((m02 * m10) * m21) * m33)) - (((m00 * m12) * m21) * m33)) - (((m01 * m10) * m22) * m33)) + (m00 * m11 * m22 * m33);
    }

    public static double[] inverse(double[] matrix) {
        double det = determinant(matrix);
        if (!isZero(det)) {
            double m00 = matrix[0];
            double m01 = matrix[1];
            double m02 = matrix[2];
            double m03 = matrix[3];
            double m10 = matrix[4];
            double m11 = matrix[5];
            double m12 = matrix[6];
            double m13 = matrix[7];
            double m20 = matrix[8];
            double m21 = matrix[9];
            double m22 = matrix[10];
            double m23 = matrix[11];
            double m30 = matrix[12];
            double m31 = matrix[13];
            double m32 = matrix[14];
            double m33 = matrix[15];
            return new double[]{(((((((m12 * m23) * m31) - ((m13 * m22) * m31)) + ((m13 * m21) * m32)) - ((m11 * m23) * m32)) - ((m12 * m21) * m33)) + ((m11 * m22) * m33)) / det, (((((((m03 * m22) * m31) - ((m02 * m23) * m31)) - ((m03 * m21) * m32)) + ((m01 * m23) * m32)) + ((m02 * m21) * m33)) - ((m01 * m22) * m33)) / det, (((((((m02 * m13) * m31) - ((m03 * m12) * m31)) + ((m03 * m11) * m32)) - ((m01 * m13) * m32)) - ((m02 * m11) * m33)) + ((m01 * m12) * m33)) / det, (((((((m03 * m12) * m21) - ((m02 * m13) * m21)) - ((m03 * m11) * m22)) + ((m01 * m13) * m22)) + ((m02 * m11) * m23)) - ((m01 * m12) * m23)) / det, (((((((m13 * m22) * m30) - ((m12 * m23) * m30)) - ((m13 * m20) * m32)) + ((m10 * m23) * m32)) + ((m12 * m20) * m33)) - ((m10 * m22) * m33)) / det, (((((((m02 * m23) * m30) - ((m03 * m22) * m30)) + ((m03 * m20) * m32)) - ((m00 * m23) * m32)) - ((m02 * m20) * m33)) + ((m00 * m22) * m33)) / det, (((((((m03 * m12) * m30) - ((m02 * m13) * m30)) - ((m03 * m10) * m32)) + ((m00 * m13) * m32)) + ((m02 * m10) * m33)) - ((m00 * m12) * m33)) / det, (((((((m02 * m13) * m20) - ((m03 * m12) * m20)) + ((m03 * m10) * m22)) - ((m00 * m13) * m22)) - ((m02 * m10) * m23)) + ((m00 * m12) * m23)) / det, (((((((m11 * m23) * m30) - ((m13 * m21) * m30)) + ((m13 * m20) * m31)) - ((m10 * m23) * m31)) - ((m11 * m20) * m33)) + ((m10 * m21) * m33)) / det, (((((((m03 * m21) * m30) - ((m01 * m23) * m30)) - ((m03 * m20) * m31)) + ((m00 * m23) * m31)) + ((m01 * m20) * m33)) - ((m00 * m21) * m33)) / det, (((((((m01 * m13) * m30) - ((m03 * m11) * m30)) + ((m03 * m10) * m31)) - ((m00 * m13) * m31)) - ((m01 * m10) * m33)) + ((m00 * m11) * m33)) / det, (((((((m03 * m11) * m20) - ((m01 * m13) * m20)) - ((m03 * m10) * m21)) + ((m00 * m13) * m21)) + ((m01 * m10) * m23)) - ((m00 * m11) * m23)) / det, (((((((m12 * m21) * m30) - ((m11 * m22) * m30)) - ((m12 * m20) * m31)) + ((m10 * m22) * m31)) + ((m11 * m20) * m32)) - ((m10 * m21) * m32)) / det, (((((((m01 * m22) * m30) - ((m02 * m21) * m30)) + ((m02 * m20) * m31)) - ((m00 * m22) * m31)) - ((m01 * m20) * m32)) + ((m00 * m21) * m32)) / det, (((((((m02 * m11) * m30) - ((m01 * m12) * m30)) - ((m02 * m10) * m31)) + ((m00 * m12) * m31)) + ((m01 * m10) * m32)) - ((m00 * m11) * m32)) / det, (((((((m01 * m12) * m20) - ((m02 * m11) * m20)) + ((m02 * m10) * m21)) - ((m00 * m12) * m21)) - ((m01 * m10) * m22)) + ((m00 * m11) * m22)) / det};
        }
        return matrix;
    }

    public static double[] transpose(double[] m) {
        return new double[]{m[0], m[4], m[8], m[12], m[1], m[5], m[9], m[13], m[2], m[6], m[10], m[14], m[3], m[7], m[11], m[15]};
    }

    public static void multiplyVectorByMatrix(double[] v, double[] m, double[] result) {
        double vx = v[0];
        double vy = v[1];
        double vz = v[2];
        double vw = v[3];
        result[0] = (m[0] * vx) + (m[4] * vy) + (m[8] * vz) + (m[12] * vw);
        result[1] = (m[1] * vx) + (m[5] * vy) + (m[9] * vz) + (m[13] * vw);
        result[2] = (m[2] * vx) + (m[6] * vy) + (m[10] * vz) + (m[14] * vw);
        result[3] = (m[3] * vx) + (m[7] * vy) + (m[11] * vz) + (m[15] * vw);
    }

    public static double v3Length(double[] a) {
        return Math.sqrt((a[0] * a[0]) + (a[1] * a[1]) + (a[2] * a[2]));
    }

    public static double[] v3Normalize(double[] vector, double norm) {
        if (isZero(norm)) {
            norm = v3Length(vector);
        }
        double im = 1.0d / norm;
        return new double[]{vector[0] * im, vector[1] * im, vector[2] * im};
    }

    public static double v3Dot(double[] a, double[] b) {
        return (a[0] * b[0]) + (a[1] * b[1]) + (a[2] * b[2]);
    }

    public static double[] v3Combine(double[] a, double[] b, double aScale, double bScale) {
        return new double[]{(a[0] * aScale) + (b[0] * bScale), (a[1] * aScale) + (b[1] * bScale), (a[2] * aScale) + (b[2] * bScale)};
    }

    public static double[] v3Cross(double[] a, double[] b) {
        return new double[]{(a[1] * b[2]) - (a[2] * b[1]), (a[2] * b[0]) - (a[0] * b[2]), (a[0] * b[1]) - (a[1] * b[0])};
    }

    public static void quaternionToDegreesXYZ(double[] q, double[] result) {
        double qx = q[0];
        double qy = q[1];
        double qz = q[2];
        double qw = q[3];
        double qw2 = qw * qw;
        double qx2 = qx * qx;
        double qy2 = qy * qy;
        double qz2 = qz * qz;
        double test = (qx * qy) + (qz * qw);
        double unit = qw2 + qx2 + qy2 + qz2;
        if (test > 0.49999d * unit) {
            result[0] = 0.0d;
            result[1] = 2.0d * Math.atan2(qx, qw) * 57.29577951308232d;
            result[2] = 90.0d;
        } else if (test < (-0.49999d) * unit) {
            result[0] = 0.0d;
            result[1] = (-2.0d) * Math.atan2(qx, qw) * 57.29577951308232d;
            result[2] = -90.0d;
        } else {
            result[0] = roundTo3Places(Math.atan2(((2.0d * qx) * qw) - ((2.0d * qy) * qz), (1.0d - (2.0d * qx2)) - (2.0d * qz2)) * 57.29577951308232d);
            result[1] = roundTo3Places(Math.atan2(((2.0d * qy) * qw) - ((2.0d * qx) * qz), (1.0d - (2.0d * qy2)) - (2.0d * qz2)) * 57.29577951308232d);
            result[2] = roundTo3Places(Math.asin((2.0d * qx * qy) + (2.0d * qz * qw)) * 57.29577951308232d);
        }
    }

    public static double roundTo3Places(double n) {
        return Math.round(1000.0d * n) * 0.001d;
    }

    public static double degreesToRadians(double degrees) {
        return (3.141592653589793d * degrees) / 180.0d;
    }

    public static void resetIdentityMatrix(double[] matrix) {
        matrix[14] = 0.0d;
        matrix[13] = 0.0d;
        matrix[12] = 0.0d;
        matrix[11] = 0.0d;
        matrix[9] = 0.0d;
        matrix[8] = 0.0d;
        matrix[7] = 0.0d;
        matrix[6] = 0.0d;
        matrix[4] = 0.0d;
        matrix[3] = 0.0d;
        matrix[2] = 0.0d;
        matrix[1] = 0.0d;
        matrix[15] = 1.0d;
        matrix[10] = 1.0d;
        matrix[5] = 1.0d;
        matrix[0] = 1.0d;
    }

    public static void applyPerspective(double[] m, double perspective) {
        m[11] = (-1.0d) / perspective;
    }

    public static void applyScaleX(double[] m, double factor) {
        m[0] = factor;
    }

    public static void applyScaleY(double[] m, double factor) {
        m[5] = factor;
    }

    public static void applyTranslate2D(double[] m, double x, double y) {
        m[12] = x;
        m[13] = y;
    }

    public static void applyTranslate3D(double[] m, double x, double y, double z) {
        m[12] = x;
        m[13] = y;
        m[14] = z;
    }

    public static void applySkewX(double[] m, double radians) {
        m[4] = Math.tan(radians);
    }

    public static void applySkewY(double[] m, double radians) {
        m[1] = Math.tan(radians);
    }

    public static void applyRotateX(double[] m, double radians) {
        m[5] = Math.cos(radians);
        m[6] = Math.sin(radians);
        m[9] = -Math.sin(radians);
        m[10] = Math.cos(radians);
    }

    public static void applyRotateY(double[] m, double radians) {
        m[0] = Math.cos(radians);
        m[2] = -Math.sin(radians);
        m[8] = Math.sin(radians);
        m[10] = Math.cos(radians);
    }

    public static void applyRotateZ(double[] m, double radians) {
        m[0] = Math.cos(radians);
        m[1] = Math.sin(radians);
        m[4] = -Math.sin(radians);
        m[5] = Math.cos(radians);
    }
}
