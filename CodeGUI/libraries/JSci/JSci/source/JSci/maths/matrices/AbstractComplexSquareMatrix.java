/* AUTO-GENERATED */
package JSci.maths.matrices;

import JSci.GlobalSettings;
import JSci.maths.Complex;
import JSci.maths.ComplexMapping;
import JSci.maths.ArrayMath;
import JSci.maths.LinearMath;
import JSci.maths.MaximumIterationsExceededException;
import JSci.maths.vectors.AbstractComplexVector;
import JSci.maths.groups.AbelianGroup;
import JSci.maths.algebras.*;
import JSci.maths.fields.*;

/**
* The AbstractComplexSquareMatrix class provides an object for encapsulating square matrices containing complex numbers.
* @version 2.3
* @author Mark Hale
*/
public abstract class AbstractComplexSquareMatrix extends AbstractComplexMatrix implements CStarAlgebra.Member, SquareMatrix {
        protected transient ComplexLUCache luCache;

        /**
        * Constructs a matrix.
        */
        protected AbstractComplexSquareMatrix(final int size) {
                super(size, size);
        }
        /**
        * Returns the real part of this complex matrix.
        * @return a double matrix
        */
        public AbstractDoubleMatrix real() {
                final double ans[][]=new double[numRows][numCols];
                for(int i=0;i<numRows;i++) {
                        for(int j=0;j<numCols;j++)
                                ans[i][j]=getElement(i,j).real();
                }
                return new DoubleSquareMatrix(ans);
        }
        /**
        * Returns the imaginary part of this complex matrix.
        * @return a double matrix
        */
        public AbstractDoubleMatrix imag() {
                final double ans[][]=new double[numRows][numCols];
                for(int i=0;i<numRows;i++) {
                        for(int j=0;j<numCols;j++)
                                ans[i][j]=getElement(i,j).imag();
                }
                return new DoubleSquareMatrix(ans);
        }
        /**
        * Returns true if this matrix is hermitian.
        */
        public boolean isHermitian() {
                return this.equals(this.hermitianAdjoint());
        }
        /**
        * Returns true if this matrix is unitary.
        */
        public boolean isUnitary() {
                return this.multiply(this.hermitianAdjoint()).equals(ComplexDiagonalMatrix.identity(numRows));
        }
        /**
        * Returns the determinant.
        */
        public Complex det() {
                if(numRows==2) {
                        return new Complex(
                                getRealElement(0,0)*getRealElement(1,1) - getImagElement(0,0)*getImagElement(1,1)
                                -getRealElement(0,1)*getRealElement(1,0) + getImagElement(0,1)*getImagElement(1,0),
                                getRealElement(0,0)*getImagElement(1,1) + getImagElement(0,0)*getRealElement(1,1)
                                -getRealElement(0,1)*getImagElement(1,0) - getImagElement(0,1)*getRealElement(1,0)
                        );
                } else {
                        int[] luPivot = new int[numRows+1];
                        final AbstractComplexSquareMatrix lu[]=this.luDecompose(luPivot);
                        double tmp;
                        double detRe=lu[1].getRealElement(0,0);
                        double detIm=lu[1].getImagElement(0,0);
                        for(int i=1;i<numRows;i++) {
                                tmp=detRe*lu[1].getRealElement(i,i) - detIm*lu[1].getImagElement(i,i);
                                detIm=detRe*lu[1].getImagElement(i,i) + detIm*lu[1].getRealElement(i,i);
                                detRe=tmp;
                        }
                        return new Complex(detRe*luPivot[numRows],detIm*luPivot[numRows]);
                }
        }
        /**
        * Returns the trace.
        */
        public Complex trace() {
                double trRe=getRealElement(0,0);
                double trIm=getImagElement(0,0);
                for(int i=1;i<numRows;i++) {
                        trRe+=getRealElement(i,i);
                        trIm+=getImagElement(i,i);
                }
                return new Complex(trRe,trIm);
        }
        /**
        * Returns the C<sup>*</sup> norm.
        */
        public double norm() {
                try {
                        return operatorNorm();
                } catch(MaximumIterationsExceededException e) {
                        throw new RuntimeException(e);
                }
        }
        /**
        * Returns the operator norm.
        * @exception MaximumIterationsExceededException If it takes more than 50 iterations to determine an eigenvalue.
        */
        public double operatorNorm() throws MaximumIterationsExceededException {
                return Math.sqrt(ArrayMath.max(LinearMath.eigenvalueSolveHermitian((AbstractComplexSquareMatrix)(this.hermitianAdjoint().multiply(this)))));
        }

//============
// OPERATIONS
//============

        /**
        * Returns the negative of this matrix.
        */
        public AbelianGroup.Member negate() {
                final double arrayRe[][]=new double[numRows][numCols];
                final double arrayIm[][]=new double[numRows][numCols];
                for(int i=0;i<numRows;i++) {
                        arrayRe[i][0]=-getRealElement(i,0);
                        arrayIm[i][0]=-getImagElement(i,0);
                        for(int j=1;j<numCols;j++) {
                                arrayRe[i][j]=-getRealElement(i,j);
                                arrayIm[i][j]=-getImagElement(i,j);
                        }
                }
                return new ComplexSquareMatrix(arrayRe,arrayIm);
        }

// ADDITION

        /**
        * Returns the addition of this matrix and another.
        * @param m a complex square matrix
        * @exception MatrixDimensionException If the matrices are not square or different sizes.
        */
        public final AbstractComplexMatrix add(final AbstractComplexMatrix m) {
                if(m instanceof AbstractComplexSquareMatrix)
                        return add((AbstractComplexSquareMatrix)m);
                else if(numRows == m.rows() && numCols == m.columns())
                        return add(new SquareMatrixAdaptor(m));
                else
                        throw new MatrixDimensionException("Matrices are different sizes.");
        }
        /**
        * Returns the addition of this matrix and another.
        * @param m a complex square matrix
        * @exception MatrixDimensionException If the matrices are different sizes.
        */
        public AbstractComplexSquareMatrix add(final AbstractComplexSquareMatrix m) {
                if(numRows==m.rows() && numCols==m.columns()) {
                        final double arrayRe[][]=new double[numRows][numCols];
                        final double arrayIm[][]=new double[numRows][numCols];
                        for(int i=0;i<numRows;i++) {
                                arrayRe[i][0] = getRealElement(i,0)+m.getRealElement(i,0);
                                arrayIm[i][0] = getImagElement(i,0)+m.getImagElement(i,0);
                                for(int j=1;j<numCols;j++) {
                                        arrayRe[i][j] = getRealElement(i,j)+m.getRealElement(i,j);
                                        arrayIm[i][j] = getImagElement(i,j)+m.getImagElement(i,j);
                                }
                        }
                        return new ComplexSquareMatrix(arrayRe,arrayIm);
                } else
                        throw new MatrixDimensionException("Matrices are different sizes.");
        }

// SUBTRACTION

        /**
        * Returns the subtraction of this matrix and another.
        * @param m a complex square matrix
        * @exception MatrixDimensionException If the matrices are not square or different sizes.
        */
        public final AbstractComplexMatrix subtract(final AbstractComplexMatrix m) {
                if(m instanceof AbstractComplexSquareMatrix)
                        return subtract((AbstractComplexSquareMatrix)m);
                else if(numRows == m.rows() && numCols == m.columns())
                        return subtract(new SquareMatrixAdaptor(m));
                else
                        throw new MatrixDimensionException("Matrices are different sizes.");
        }
        /**
        * Returns the subtraction of this matrix by another.
        * @param m a complex square matrix
        * @exception MatrixDimensionException If the matrices are different sizes.
        */
        public AbstractComplexSquareMatrix subtract(final AbstractComplexSquareMatrix m) {
                if(numRows==m.rows() && numCols==m.columns()) {
                        final double arrayRe[][]=new double[numRows][numCols];
                        final double arrayIm[][]=new double[numRows][numCols];
                        for(int i=0;i<numRows;i++) {
                                arrayRe[i][0] = getRealElement(i,0)-m.getRealElement(i,0);
                                arrayIm[i][0] = getImagElement(i,0)-m.getImagElement(i,0);
                                for(int j=1;j<numCols;j++) {
                                        arrayRe[i][j] = getRealElement(i,j)-m.getRealElement(i,j);
                                        arrayIm[i][j] = getImagElement(i,j)-m.getImagElement(i,j);
                                }
                        }
                        return new ComplexSquareMatrix(arrayRe,arrayIm);
                } else
                        throw new MatrixDimensionException("Matrices are different sizes.");
        }

// SCALAR MULTIPLICATION

        /**
        * Returns the multiplication of this matrix by a scalar.
        * @param z a complex number
        * @return a complex square matrix
        */
        public AbstractComplexMatrix scalarMultiply(final Complex z) {
                final double real=z.real();
                final double imag=z.imag();
                final double arrayRe[][]=new double[numRows][numCols];
                final double arrayIm[][]=new double[numRows][numCols];
                for(int i=0;i<numRows;i++) {
                        arrayRe[i][0] = real*getRealElement(i,0)-imag*getImagElement(i,0);
                        arrayIm[i][0] = imag*getRealElement(i,0)+real*getImagElement(i,0);
                        for(int j=1;j<numCols;j++) {
                                arrayRe[i][j] = real*getRealElement(i,j)-imag*getImagElement(i,j);
                                arrayIm[i][j] = imag*getRealElement(i,j)+real*getImagElement(i,j);
                        }
                }
                return new ComplexSquareMatrix(arrayRe,arrayIm);
        }
        /**
        * Returns the multiplication of this matrix by a scalar.
        * @param x a double
        * @return a complex square matrix
        */
        public AbstractComplexMatrix scalarMultiply(final double x) {
                final double arrayRe[][]=new double[numRows][numCols];
                final double arrayIm[][]=new double[numRows][numCols];
                for(int i=0;i<numRows;i++) {
                        arrayRe[i][0] = x*getRealElement(i,0);
                        arrayIm[i][0] = x*getImagElement(i,0);
                        for(int j=1;j<numCols;j++) {
                                arrayRe[i][j] = x*getRealElement(i,j);
                                arrayIm[i][j] = x*getImagElement(i,j);
                        }
                }
                return new ComplexSquareMatrix(arrayRe,arrayIm);
        }

// SCALAR DIVISON

        /**
        * Returns the division of this matrix by a scalar.
        * @param z a complex number
        * @return a complex square matrix
        */
        public AbstractComplexMatrix scalarDivide(final Complex z) {
                final Complex array[][]=new Complex[numRows][numCols];
                for(int i=0;i<numRows;i++) {
                        array[i][0] = getElement(i,0).divide(z);
                        for(int j=1;j<numCols;j++)
                                array[i][j] = getElement(i,j).divide(z);
                }
                return new ComplexSquareMatrix(array);
        }
        /**
        * Returns the division of this matrix by a scalar.
        * @param x a double
        * @return a complex square matrix
        */
        public AbstractComplexMatrix scalarDivide(final double x) {
                final double arrayRe[][]=new double[numRows][numCols];
                final double arrayIm[][]=new double[numRows][numCols];
                for(int i=0;i<numRows;i++) {
                        arrayRe[i][0]=getRealElement(i,0)/x;
                        arrayIm[i][0]=getImagElement(i,0)/x;
                        for(int j=1;j<numCols;j++) {
                                arrayRe[i][j]=getRealElement(i,j)/x;
                                arrayIm[i][j]=getImagElement(i,j)/x;
                        }
                }
                return new ComplexSquareMatrix(arrayRe,arrayIm);
        }

// SCALAR PRODUCT

        /**
        * Returns the scalar product of this matrix and another.
        * @param m a Complex square matrix.
        * @exception MatrixDimensionException If the matrices are not square or different sizes.
        */
        public final Complex scalarProduct(final AbstractComplexMatrix m) {
                if(m instanceof AbstractComplexSquareMatrix)
                        return scalarProduct((AbstractComplexSquareMatrix)m);
                else if(numRows == m.rows() && numCols == m.columns())
                        return scalarProduct(new SquareMatrixAdaptor(m));
                else
                        throw new MatrixDimensionException("Matrices are different sizes.");
        }
        /**
        * Returns the scalar product of this matrix and another.
        * @param m a complex square matrix.
        * @exception MatrixDimensionException If the matrices are different sizes.
        */
        public Complex scalarProduct(final AbstractComplexSquareMatrix m) {
                if(numRows==m.rows() && numCols==m.columns()) {
                        double real = 0.0, imag = 0.0;
                        for(int i=0; i<numRows; i++) {
                                real += getRealElement(i,0)*m.getRealElement(i,0) + getImagElement(i,0)*m.getImagElement(i,0);
                                imag += getImagElement(i,0)*m.getRealElement(i,0) - getRealElement(i,0)*m.getImagElement(i,0);
                                for(int j=1; j<numCols; j++) {
                                        real += getRealElement(i,j)*m.getRealElement(i,j) + getImagElement(i,j)*m.getImagElement(i,j);
                                        imag += getImagElement(i,j)*m.getRealElement(i,j) - getRealElement(i,j)*m.getImagElement(i,j);
                                }
                        }
                        return new Complex(real, imag);
                } else {
                       throw new MatrixDimensionException("Matrices are different sizes.");
                }
        }

// MATRIX MULTIPLICATION

        /**
        * Returns the multiplication of this matrix and another.
        * @param m a complex square matrix
        * @return an AbstractComplexMatrix or an AbstractComplexSquareMatrix as appropriate
        * @exception MatrixDimensionException If the matrices are incompatible.
        */
        public AbstractComplexSquareMatrix multiply(final AbstractComplexSquareMatrix m) {
                if(numCols==m.rows()) {
                        final double arrayRe[][]=new double[numRows][m.columns()];
                        final double arrayIm[][]=new double[numRows][m.columns()];
                        Complex tmp;
                        for(int j=0;j<numRows;j++) {
                                for(int k=0;k<m.columns();k++) {
                                        tmp=getElement(j,0).multiply(m.getElement(0,k));
                                        arrayRe[j][k]=tmp.real();
                                        arrayIm[j][k]=tmp.imag();
                                        for(int n=1;n<numCols;n++) {
                                                tmp=getElement(j,n).multiply(m.getElement(n,k));
                                                arrayRe[j][k]+=tmp.real();
                                                arrayIm[j][k]+=tmp.imag();
                                        }
                                }
                        }
                        return new ComplexSquareMatrix(arrayRe,arrayIm);
                } else {
                        throw new MatrixDimensionException("Incompatible matrices.");
                }
        }

// DIRECT SUM

        /**
        * Returns the direct sum of this matrix and another.
        */
        public AbstractComplexSquareMatrix directSum(final AbstractComplexSquareMatrix m) {
                final double arrayRe[][]=new double[numRows+m.numRows][numCols+m.numCols];
                final double arrayIm[][]=new double[numRows+m.numRows][numCols+m.numCols];
                for(int j,i=0;i<numRows;i++) {
                        for(j=0;j<numCols;j++) {
                                arrayRe[i][j]=getRealElement(i,j);
                                arrayIm[i][j]=getImagElement(i,j);
                        }
                }
                for(int j,i=0;i<m.numRows;i++) {
                        for(j=0;j<m.numCols;j++) {
                                arrayRe[i+numRows][j+numCols]=m.getRealElement(i,j);
                                arrayIm[i+numRows][j+numCols]=m.getImagElement(i,j);
                        }
                }
                return new ComplexSquareMatrix(arrayRe,arrayIm);
        }

// TENSOR PRODUCT

        /**
        * Returns the tensor product of this matrix and another.
        */
        public AbstractComplexSquareMatrix tensor(final AbstractComplexSquareMatrix m) {
                final double arrayRe[][]=new double[numRows*m.numRows][numCols*m.numCols];
                final double arrayIm[][]=new double[numRows*m.numRows][numCols*m.numCols];
                for(int i=0;i<numRows;i++) {
                        for(int j=0;j<numCols;j++) {
                                for(int k=0;k<m.numRows;j++) {
                                        for(int l=0;l<m.numCols;l++) {
                                                Complex tmp=getElement(i,j).multiply(m.getElement(k,l));
                                                arrayRe[i*m.numRows+k][j*m.numCols+l]=tmp.real();
                                                arrayIm[i*m.numRows+k][j*m.numCols+l]=tmp.imag();
                                        }
                                }
                        }
                }
                return new ComplexSquareMatrix(arrayRe,arrayIm);
        }

// HERMITIAN ADJOINT

        /**
        * Returns the involution of this matrix.
        */
        public final CStarAlgebra.Member involution() {
                return (CStarAlgebra.Member) hermitianAdjoint();
        }
        /**
        * Returns the hermitian adjoint of this matrix.
        * @return a complex square matrix
        */
        public AbstractComplexMatrix hermitianAdjoint() {
                final double arrayRe[][]=new double[numCols][numRows];
                final double arrayIm[][]=new double[numCols][numRows];
                for(int i=0;i<numRows;i++) {
                        arrayRe[0][i]=getRealElement(i,0);
                        arrayIm[0][i]=-getImagElement(i,0);
                        for(int j=1;j<numCols;j++) {
                                arrayRe[j][i]=getRealElement(i,j);
                                arrayIm[j][i]=-getImagElement(i,j);
                        }
                }
                return new ComplexSquareMatrix(arrayRe,arrayIm);
        }

// CONJUGATE

        /**
        * Returns the complex conjugate of this matrix.
        * @return a complex square matrix
        */
        public AbstractComplexMatrix conjugate() {
                final double arrayRe[][]=new double[numCols][numRows];
                final double arrayIm[][]=new double[numCols][numRows];
                for(int i=0;i<numRows;i++) {
                        arrayRe[i][0]=getRealElement(i,0);
                        arrayIm[i][0]=-getImagElement(i,0);
                        for(int j=1;j<numCols;j++) {
                                arrayRe[i][j]=getRealElement(i,j);
                                arrayIm[i][j]=-getImagElement(i,j);
                        }
                }
                return new ComplexSquareMatrix(arrayRe,arrayIm);
        }

// TRANSPOSE

        /**
        * Returns the transpose of this matrix.
        * @return a complex square matrix
        */
        public Matrix transpose() {
                final double arrayRe[][]=new double[numCols][numRows];
                final double arrayIm[][]=new double[numCols][numRows];
                for(int i=0;i<numRows;i++) {
                        arrayRe[0][i]=getRealElement(i,0);
                        arrayIm[0][i]=getImagElement(i,0);
                        for(int j=1;j<numCols;j++) {
                                arrayRe[j][i]=getRealElement(i,j);
                                arrayIm[j][i]=getImagElement(i,j);
                        }
                }
                return new ComplexSquareMatrix(arrayRe,arrayIm);
        }

// INVERSE

        /**
        * Returns the inverse of this matrix.
        * @return a complex square matrix
        */
        public AbstractComplexSquareMatrix inverse() {
                final int N=numRows;
                final double arrayLRe[][]=new double[N][N];
                final double arrayLIm[][]=new double[N][N];
                final double arrayURe[][]=new double[N][N];
                final double arrayUIm[][]=new double[N][N];
                final int[] luPivot = new int[numRows+1];
                final AbstractComplexSquareMatrix lu[]=this.luDecompose(luPivot);
                double denom;
                denom = (lu[0].getRealElement(0,0)*lu[0].getRealElement(0,0) + lu[0].getImagElement(0,0)*lu[0].getImagElement(0,0));
                arrayLRe[0][0] = lu[0].getRealElement(0,0)/denom;
                arrayLIm[0][0] = -lu[0].getImagElement(0,0)/denom;
                denom = (lu[1].getRealElement(0,0)*lu[1].getRealElement(0,0) + lu[1].getImagElement(0,0)*lu[1].getImagElement(0,0));
                arrayURe[0][0] = lu[1].getRealElement(0,0)/denom;
                arrayUIm[0][0] = -lu[1].getImagElement(0,0)/denom;
                for(int i=1;i<N;i++) {
                        denom = (lu[0].getRealElement(i,i)*lu[0].getRealElement(i,i) + lu[0].getImagElement(i,i)*lu[0].getImagElement(i,i));
                        arrayLRe[i][i] = lu[0].getRealElement(i,i)/denom;
                        arrayLIm[i][i] = -lu[0].getImagElement(i,i)/denom;
                        denom = (lu[1].getRealElement(i,i)*lu[1].getRealElement(i,i) + lu[1].getImagElement(i,i)*lu[1].getImagElement(i,i));
                        arrayURe[i][i] = lu[1].getRealElement(i,i)/denom;
                        arrayUIm[i][i] = -lu[1].getImagElement(i,i)/denom;
                }
                for(int i=0;i<N-1;i++) {
                        for(int j=i+1;j<N;j++) {
                                double tmpLRe=0.0, tmpLIm=0.0;
                                double tmpURe=0.0, tmpUIm=0.0;
                                for(int k=i;k<j;k++) {
                                        tmpLRe-=(lu[0].getRealElement(j,k)*arrayLRe[k][i] - lu[0].getImagElement(j,k)*arrayLIm[k][i]);
                                        tmpLIm-=(lu[0].getImagElement(j,k)*arrayLRe[k][i] + lu[0].getRealElement(j,k)*arrayLIm[k][i]);
                                        tmpURe-=(arrayURe[i][k]*lu[1].getRealElement(k,j) - arrayUIm[i][k]*lu[1].getImagElement(k,j));
                                        tmpUIm-=(arrayUIm[i][k]*lu[1].getRealElement(k,j) + arrayURe[i][k]*lu[1].getImagElement(k,j));
                                }
                                denom = (lu[0].getRealElement(j,j)*lu[0].getRealElement(j,j) + lu[0].getImagElement(j,j)*lu[0].getImagElement(j,j));
                                arrayLRe[j][i]=(tmpLRe*lu[0].getRealElement(j,j)+tmpLIm*lu[0].getImagElement(j,j))/denom;
                                arrayLIm[j][i]=(tmpLIm*lu[0].getRealElement(j,j)-tmpLRe*lu[0].getImagElement(j,j))/denom;
                                denom = (lu[1].getRealElement(j,j)*lu[1].getRealElement(j,j) + lu[1].getImagElement(j,j)*lu[1].getImagElement(j,j));
                                arrayURe[i][j]=(tmpURe*lu[1].getRealElement(j,j)+tmpUIm*lu[1].getImagElement(j,j))/denom;
                                arrayUIm[i][j]=(tmpUIm*lu[1].getRealElement(j,j)-tmpURe*lu[1].getImagElement(j,j))/denom;
                        }
                }
                // matrix multiply arrayU x arrayL
                final double invRe[][]=new double[N][N];
                final double invIm[][]=new double[N][N];
                for(int i=0;i<N;i++) {
                        for(int j=0;j<i;j++) {
                                for(int k=i;k<N;k++) {
                                        invRe[i][luPivot[j]]+=(arrayURe[i][k]*arrayLRe[k][j] - arrayUIm[i][k]*arrayLIm[k][j]);
                                        invIm[i][luPivot[j]]+=(arrayUIm[i][k]*arrayLRe[k][j] + arrayURe[i][k]*arrayLIm[k][j]);
                                }
                        }
                        for(int j=i;j<N;j++) {
                                for(int k=j;k<N;k++) {
                                        invRe[i][luPivot[j]]+=(arrayURe[i][k]*arrayLRe[k][j] - arrayUIm[i][k]*arrayLIm[k][j]);
                                        invIm[i][luPivot[j]]+=(arrayUIm[i][k]*arrayLRe[k][j] + arrayURe[i][k]*arrayLIm[k][j]);
                                }
                        }
                }
                return new ComplexSquareMatrix(invRe,invIm);
        }

// LU DECOMPOSITION

        /**
        * Returns the LU decomposition of this matrix.
        * @return an array with [0] containing the L-matrix and [1] containing the U-matrix.
        */
        public AbstractComplexSquareMatrix[] luDecompose(int pivot[]) {
                AbstractComplexSquareMatrix[] LU = luDecompose_cache(pivot);
                if(LU != null)
                    return LU;
                final int N=numRows;
                final double arrayLRe[][]=new double[N][N];
                final double arrayLIm[][]=new double[N][N];
                final double arrayURe[][]=new double[N][N];
                final double arrayUIm[][]=new double[N][N];
                if(pivot==null)
                        pivot=new int[N+1];
                for(int i=0;i<N;i++)
                        pivot[i]=i;
                pivot[N]=1;
        // LU decomposition to arrayU
                for(int j=0;j<N;j++) {
                        for(int i=0;i<j;i++) {
                                double tmpRe = getRealElement(pivot[i],j);
                                double tmpIm = getImagElement(pivot[i],j);
                                for(int k=0;k<i;k++) {
                                        tmpRe-=(arrayURe[i][k]*arrayURe[k][j] - arrayUIm[i][k]*arrayUIm[k][j]);
                                        tmpIm-=(arrayUIm[i][k]*arrayURe[k][j] + arrayURe[i][k]*arrayUIm[k][j]);
                                }
                                arrayURe[i][j]=tmpRe;
                                arrayUIm[i][j]=tmpIm;
                        }
                        double max=0.0;
                        int pivotrow=j;
                        for(int i=j;i<N;i++) {
                                double tmpRe = getRealElement(pivot[i],j);
                                double tmpIm = getImagElement(pivot[i],j);
                                for(int k=0;k<j;k++) {
                                        tmpRe-=(arrayURe[i][k]*arrayURe[k][j] - arrayUIm[i][k]*arrayUIm[k][j]);
                                        tmpIm-=(arrayUIm[i][k]*arrayURe[k][j] + arrayURe[i][k]*arrayUIm[k][j]);
                                }
                                arrayURe[i][j]=tmpRe;
                                arrayUIm[i][j]=tmpIm;
                        // while we're here search for a pivot for arrayU[j][j]
                                double tmp=tmpRe*tmpRe+tmpIm*tmpIm;
                                if(tmp>max) {
                                        max=tmp;
                                        pivotrow=i;
                                }
                        }
                // swap row j with pivotrow
                        if(pivotrow!=j) {
                                double[] tmprow = arrayURe[j];
                                arrayURe[j] = arrayURe[pivotrow];
                                arrayURe[pivotrow] = tmprow;
                                tmprow = arrayUIm[j];
                                arrayUIm[j] = arrayUIm[pivotrow];
                                arrayUIm[pivotrow] = tmprow;
                                int k=pivot[j];
                                pivot[j]=pivot[pivotrow];
                                pivot[pivotrow]=k;
                                // update parity
                                pivot[N]=-pivot[N];
                        }
                // divide by pivot
                        double tmpRe=arrayURe[j][j];
                        double tmpIm=arrayUIm[j][j];
                        if(Math.abs(tmpRe)<Math.abs(tmpIm)) {
                                double a=tmpRe/tmpIm;
                                double denom=tmpRe*a+tmpIm;
                                for(int i=j+1;i<N;i++) {
                                        double tmp=(arrayURe[i][j]*a+arrayUIm[i][j])/denom;
                                        arrayUIm[i][j]=(arrayUIm[i][j]*a-arrayURe[i][j])/denom;
                                        arrayURe[i][j]=tmp;
                                }
                        } else {
                                double a=tmpIm/tmpRe;
                                double denom=tmpRe+tmpIm*a;
                                for(int i=j+1;i<N;i++) {
                                        double tmp=(arrayURe[i][j]+arrayUIm[i][j]*a)/denom;
                                        arrayUIm[i][j]=(arrayUIm[i][j]-arrayURe[i][j]*a)/denom;
                                        arrayURe[i][j]=tmp;
                                }
                        }
                }
                // move lower triangular part to arrayL
                for(int j=0;j<N;j++) {
                        arrayLRe[j][j]=1.0;
                        for(int i=j+1;i<N;i++) {
                                arrayLRe[i][j]=arrayURe[i][j];
                                arrayLIm[i][j]=arrayUIm[i][j];
                                arrayURe[i][j]=0.0;
                                arrayUIm[i][j]=0.0;
                        }
                }
                AbstractComplexSquareMatrix L=new ComplexSquareMatrix(arrayLRe,arrayLIm);
                AbstractComplexSquareMatrix U=new ComplexSquareMatrix(arrayURe,arrayUIm);
                int[] LUpivot=new int[pivot.length];
                System.arraycopy(pivot,0,LUpivot,0,pivot.length);
                luCache = new ComplexLUCache(L, U, LUpivot);
                return new AbstractComplexSquareMatrix[] {L,U};
        }
        protected AbstractComplexSquareMatrix[] luDecompose_cache(int pivot[]) {
                if(luCache != null) {
                        AbstractComplexSquareMatrix L = luCache.getL();
                        AbstractComplexSquareMatrix U = luCache.getU();
                        if(L != null && U != null) {
                            if(pivot == null) {
                                return new AbstractComplexSquareMatrix[] {L, U};
                            } else {
                                int[] LUpivot = luCache.getPivot();
                                if(LUpivot != null) {
                                    System.arraycopy(LUpivot,0,pivot,0,pivot.length);
                                    return new AbstractComplexSquareMatrix[] {L, U};
                                }
                            }
                        }
                        luCache = null;
                }
                return null;
        }
        /**
        * Returns the LU decomposition of this matrix.
        * Warning: no pivoting.
        * @return an array with [0] containing the L-matrix
        * and [1] containing the U-matrix.
        * @jsci.planetmath LUDecomposition
        */
        public AbstractComplexSquareMatrix[] luDecompose() {
                final int N=numRows;
                final double arrayLRe[][]=new double[N][N];
                final double arrayLIm[][]=new double[N][N];
                final double arrayURe[][]=new double[N][N];
                final double arrayUIm[][]=new double[N][N];
        // LU decomposition to arrayU
                for(int j=0;j<N;j++) {
                        for(int i=0;i<j;i++) {
                                double tmpRe = getRealElement(i,j);
                                double tmpIm = getImagElement(i,j);
                                for(int k=0;k<i;k++) {
                                        tmpRe-=(arrayURe[i][k]*arrayURe[k][j] - arrayUIm[i][k]*arrayUIm[k][j]);
                                        tmpIm-=(arrayUIm[i][k]*arrayURe[k][j] + arrayURe[i][k]*arrayUIm[k][j]);
                                }
                                arrayURe[i][j]=tmpRe;
                                arrayUIm[i][j]=tmpIm;
                        }
                        for(int i=j;i<N;i++) {
                                double tmpRe = getRealElement(i,j);
                                double tmpIm = getImagElement(i,j);
                                for(int k=0;k<j;k++) {
                                        tmpRe-=(arrayURe[i][k]*arrayURe[k][j] - arrayUIm[i][k]*arrayUIm[k][j]);
                                        tmpIm-=(arrayUIm[i][k]*arrayURe[k][j] + arrayURe[i][k]*arrayUIm[k][j]);
                                }
                                arrayURe[i][j]=tmpRe;
                                arrayUIm[i][j]=tmpIm;
                        }
                // divide
                        double tmpRe=arrayURe[j][j];
                        double tmpIm=arrayUIm[j][j];
                        if(Math.abs(tmpRe)<Math.abs(tmpIm)) {
                                double a=tmpRe/tmpIm;
                                double denom=tmpRe*a+tmpIm;
                                for(int i=j+1;i<N;i++) {
                                        double tmp=(arrayURe[i][j]*a+arrayUIm[i][j])/denom;
                                        arrayUIm[i][j]=(arrayUIm[i][j]*a-arrayURe[i][j])/denom;
                                        arrayURe[i][j]=tmp;
                                }
                        } else {
                                double a=tmpIm/tmpRe;
                                double denom=tmpRe+tmpIm*a;
                                for(int i=j+1;i<N;i++) {
                                        double tmp=(arrayURe[i][j]+arrayUIm[i][j]*a)/denom;
                                        arrayUIm[i][j]=(arrayUIm[i][j]-arrayURe[i][j]*a)/denom;
                                        arrayURe[i][j]=tmp;
                                }
                        }
                }
                // move lower triangular part to arrayL
                for(int j=0;j<N;j++) {
                        arrayLRe[j][j]=1.0;
                        for(int i=j+1;i<N;i++) {
                                arrayLRe[i][j]=arrayURe[i][j];
                                arrayLIm[i][j]=arrayUIm[i][j];
                                arrayURe[i][j]=0.0;
                                arrayUIm[i][j]=0.0;
                        }
                }
                AbstractComplexSquareMatrix[] lu=new AbstractComplexSquareMatrix[2];
                lu[0]=new ComplexSquareMatrix(arrayLRe,arrayLIm);
                lu[1]=new ComplexSquareMatrix(arrayURe,arrayUIm);
                return lu;
        }

// POLAR DECOMPOSITION

        /**
        * Returns the polar decomposition of this matrix.
        */
        public AbstractComplexSquareMatrix[] polarDecompose() {
                final int N=numRows;
                final AbstractComplexVector evec[]=new AbstractComplexVector[N];
                double eval[];
                try {
                        eval=LinearMath.eigenSolveHermitian(this,evec);
                } catch(MaximumIterationsExceededException e) {
                        return null;
                }
                final double tmpaRe[][]=new double[N][N];
                final double tmpaIm[][]=new double[N][N];
                final double tmpmRe[][]=new double[N][N];
                final double tmpmIm[][]=new double[N][N];
                double abs;
                Complex comp;
                for(int i=0;i<N;i++) {
                        abs=Math.abs(eval[i]);
                        comp=evec[i].getComponent(0).conjugate();
                        tmpaRe[i][0]=eval[i]*comp.real()/abs;
                        tmpaIm[i][0]=eval[i]*comp.imag()/abs;
                        tmpmRe[i][0]=abs*comp.real();
                        tmpmIm[i][0]=abs*comp.imag();
                        for(int j=1;j<N;j++) {
                                comp=evec[i].getComponent(j).conjugate();
                                tmpaRe[i][j]=eval[i]*comp.real()/abs;
                                tmpaIm[i][j]=eval[i]*comp.imag()/abs;
                                tmpmRe[i][j]=abs*comp.real();
                                tmpmIm[i][j]=abs*comp.imag();
                        }
                }
                final double argRe[][]=new double[N][N];
                final double argIm[][]=new double[N][N];
                final double modRe[][]=new double[N][N];
                final double modIm[][]=new double[N][N];
                for(int i=0;i<N;i++) {
                        for(int j=0;j<N;j++) {
                                comp=evec[0].getComponent(i);
                                argRe[i][j]=(tmpaRe[0][j]*comp.real() - tmpaIm[0][j]*comp.imag());
                                argIm[i][j]=(tmpaIm[0][j]*comp.real() + tmpaRe[0][j]*comp.imag());
                                modRe[i][j]=(tmpmRe[0][j]*comp.real() - tmpmIm[0][j]*comp.imag());
                                modIm[i][j]=(tmpmIm[0][j]*comp.real() + tmpmRe[0][j]*comp.imag());
                                for(int k=1;k<N;k++) {
                                        comp=evec[k].getComponent(i);
                                        argRe[i][j]+=(tmpaRe[k][j]*comp.real() - tmpaIm[k][j]*comp.imag());
                                        argIm[i][j]+=(tmpaIm[k][j]*comp.real() + tmpaRe[k][j]*comp.imag());
                                        modRe[i][j]+=(tmpmRe[k][j]*comp.real() - tmpmIm[k][j]*comp.imag());
                                        modIm[i][j]+=(tmpmIm[k][j]*comp.real() + tmpmRe[k][j]*comp.imag());
                                }
                        }
                }
                final AbstractComplexSquareMatrix us[]=new AbstractComplexSquareMatrix[2];
                us[0]=new ComplexSquareMatrix(argRe,argIm);
                us[1]=new ComplexSquareMatrix(modRe,modIm);
                return us;
        }

// MAP ELEMENTS

        /**
        * Applies a function on all the matrix elements.
        * @param f a user-defined function
        * @return a complex square matrix
        */
        public AbstractComplexMatrix mapElements(final ComplexMapping f) {
                final Complex array[][]=new Complex[numRows][numCols];
                for(int i=0;i<numRows;i++) {
                        array[i][0]=f.map(getElement(i,0));
                        for(int j=1;j<numCols;j++)
                                array[i][j]=f.map(getElement(i,j));
                }
                return new ComplexSquareMatrix(array);
        }

        private static class SquareMatrixAdaptor extends AbstractComplexSquareMatrix {
                private final AbstractComplexMatrix matrix;
                private SquareMatrixAdaptor(AbstractComplexMatrix m) {
                        super(m.rows());
                        matrix = m;
                }
                public Complex getElement(int i, int j) {
                        return matrix.getElement(i,j);
                }
                public double getRealElement(int i, int j) {
                        return matrix.getRealElement(i,j);
                }
                public double getImagElement(int i, int j) {
                        return matrix.getImagElement(i,j);
                }
                public void setElement(int i, int j, Complex z) {
                        matrix.setElement(i,j, z);
                }
                public void setElement(int i, int j, double x, double y) {
                        matrix.setElement(i,j, x,y);
                }
        }
}
