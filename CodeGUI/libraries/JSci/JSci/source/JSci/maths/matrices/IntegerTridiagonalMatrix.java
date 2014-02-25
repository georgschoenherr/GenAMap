/* AUTO-GENERATED */
package JSci.maths.matrices;

import JSci.maths.ArrayMath;
import JSci.maths.ExtraMath;
import JSci.maths.Mapping;
import JSci.maths.LinearMath;
import JSci.maths.MaximumIterationsExceededException;
import JSci.maths.DimensionException;
import JSci.maths.vectors.AbstractIntegerVector;
import JSci.maths.vectors.IntegerVector;
import JSci.maths.groups.AbelianGroup;
import JSci.maths.algebras.*;
import JSci.maths.fields.*;

/**
* The IntegerTridiagonalMatrix class provides an object for encapsulating integer tridiagonal matrices.
* @version 2.2
* @author Mark Hale
*/
public class IntegerTridiagonalMatrix extends AbstractIntegerSquareMatrix implements TridiagonalMatrix {
        /**
        * Tridiagonal data.
        */
        protected final int ldiag[];
        protected final int diag[];
        protected final int udiag[];
        /**
        * Constructs an empty matrix.
        * @param size the number of rows/columns
        */
        public IntegerTridiagonalMatrix(final int size) {
                super(size);
                ldiag=new int[size];
                diag=new int[size];
                udiag=new int[size];
        }
        /**
        * Constructs a matrix from an array.
        * Any non-tridiagonal elements in the array are ignored.
        * @param array an assigned value
        * @exception MatrixDimensionException If the array is not square.
        */
        public IntegerTridiagonalMatrix(final int array[][]) {
                this(array.length);
                if(!ArrayMath.isSquare(array))
                        throw new MatrixDimensionException("Array is not square.");
                diag[0]=array[0][0];
                udiag[0]=array[0][1];
                int i=1;
                for(;i<array.length-1;i++) {
                        ldiag[i]=array[i][i-1];
                        diag[i]=array[i][i];
                        udiag[i]=array[i][i+1];
                }
                ldiag[i]=array[i][i-1];
                diag[i]=array[i][i];
        }
        /**
        * Compares two ${nativeTyp} matrices for equality.
        * @param m a int matrix
        */
        public boolean equals(AbstractIntegerMatrix m, double tol) {
                if(m instanceof TridiagonalMatrix) {
                        if(numRows != m.rows() || numCols != m.columns())
                                return false;
			int sumSqr = 0;
			int ldelta;
			int delta = diag[0] - m.getElement(0,0);
			int udelta = udiag[0] - m.getElement(0,1);
			sumSqr += delta*delta + udelta*udelta;
                        int i=1;
                        for(;i<numRows-1;i++) {
				ldelta = ldiag[i] - m.getElement(i,i-1);
				delta = diag[i] - m.getElement(i,i);
				udelta = udiag[i] - m.getElement(i,i+1);
				sumSqr += ldelta*ldelta + delta*delta + udelta*udelta;
                        }
			ldelta = ldiag[i] - m.getElement(i,i-1);
			delta = diag[i] - m.getElement(i,i);
			sumSqr += ldelta*ldelta + delta*delta;
                        return (sumSqr <= tol*tol);
                } else {
                        return false;
                }
        }
        /**
        * Returns a string representing this matrix.
        */
        public String toString() {
                final StringBuffer buf=new StringBuffer(5*numRows*numCols);
                for(int i=0;i<numRows;i++) {
                        for(int j=0;j<numCols;j++) {
                                buf.append(getElement(i,j));
                                buf.append(' ');
                        }
                        buf.append('\n');
                }
                return buf.toString();
        }
        /**
        * Converts this matrix to a double matrix.
        * @return a double matrix
        */
        public AbstractDoubleMatrix toDoubleMatrix() {
                final DoubleTridiagonalMatrix m=new DoubleTridiagonalMatrix(numRows);
                m.diag[0]=diag[0];
                m.udiag[0]=udiag[0];
                int i=1;
                for(;i<numRows-1;i++) {
                        m.ldiag[i]=ldiag[i];
                        m.diag[i]=diag[i];
                        m.udiag[i]=udiag[i];
                }
                m.ldiag[i]=ldiag[i];
                m.diag[i]=diag[i];
                return m;
        }
        /**
        * Converts this matrix to a complex matrix.
        * @return a complex matrix
        */
        public AbstractComplexMatrix toComplexMatrix() {
                final ComplexTridiagonalMatrix m=new ComplexTridiagonalMatrix(numRows);
                m.diagRe[0]=diag[0];
                m.udiagRe[0]=udiag[0];
                int i=1;
                for(;i<numRows-1;i++) {
                        m.ldiagRe[i]=ldiag[i];
                        m.diagRe[i]=diag[i];
                        m.udiagRe[i]=udiag[i];
                }
                m.ldiagRe[i]=ldiag[i];
                m.diagRe[i]=diag[i];
                return m;
        }
        /**
        * Returns an element of the matrix.
        * @param i row index of the element
        * @param j column index of the element
        * @exception MatrixDimensionException If attempting to access an invalid element.
        */
        public int getElement(int i, int j) {
                if(i>=0 && i<numRows && j>=0 && j<numCols) {
                        if(j == i-1)
                                return ldiag[i];
                        else if(j == i)
                                return diag[i];
                        else if(j == i+1)
                                return udiag[i];
                        else
                                return 0;
                } else
                        throw new MatrixDimensionException(getInvalidElementMsg(i,j));
        }
        /**
        * Sets the value of an element of the matrix.
        * Should only be used to initialise this matrix.
        * @param i row index of the element
        * @param j column index of the element
        * @param x a number
        * @exception MatrixDimensionException If attempting to access an invalid element.
        */
        public void setElement(int i, int j, final int x) {
                if(i>=0 && i<numRows && j>=0 && j<numCols) {
                        if(j == i-1)
                                ldiag[i] = x;
                        else if(j == i)
                                diag[i] = x;
                        else if(j == i+1)
                                udiag[i] = x;
                        else
                                throw new MatrixDimensionException(getInvalidElementMsg(i,j));
                } else
                        throw new MatrixDimensionException(getInvalidElementMsg(i,j));
        }
        /**
        * Returns true if this matrix is symmetric.
        */
        public boolean isSymmetric() {
                if(ldiag[1]!=udiag[0])
                        return false;
                for(int i=1;i<numRows-1;i++) {
                        if(ldiag[i+1]!=udiag[i])
                                return false;
                }
                return true;
        }
        /**
        * Returns the trace.
        */
        public int trace() {
                int tr=diag[0];
                for(int i=1;i<numRows;i++)
                        tr+=diag[i];
                return tr;
        }
        /**
        * Returns the l<sup><img border=0 alt="infinity" src="doc-files/infinity.gif"></sup>-norm.
        * @author Taber Smith
        */
        public int infNorm() {
                int result=Math.abs(diag[0])+Math.abs(udiag[0]);
                int tmpResult;
                int i=1;
                for(;i<numRows-1;i++) {
                        tmpResult=Math.abs(ldiag[i])+Math.abs(diag[i])+Math.abs(udiag[i]);
                        if(tmpResult>result)
                                result=tmpResult;
                }
                tmpResult=Math.abs(ldiag[i])+Math.abs(diag[i]);
                if(tmpResult>result)
                        result=tmpResult;
                return result;
        }
        /**
        * Returns the Frobenius (l<sup>2</sup>) norm.
        * @author Taber Smith
        */
        public double frobeniusNorm() {
                double result=diag[0]*diag[0]+udiag[0]*udiag[0];
                int i=1;
                for(;i<numRows-1;i++)
                        result+=ldiag[i]*ldiag[i]+diag[i]*diag[i]+udiag[i]*udiag[i];
                result+=ldiag[i]*ldiag[i]+diag[i]*diag[i];
                return Math.sqrt(result);
        }
        /**
        * Returns the operator norm.
        * @exception MaximumIterationsExceededException If it takes more than 50 iterations to determine an eigenvalue.
        */
        public double operatorNorm() throws MaximumIterationsExceededException {
                return Math.sqrt(ArrayMath.max(LinearMath.eigenvalueSolveSymmetric((DoubleTridiagonalMatrix)(this.transpose().multiply(this)))));
        }

//============
// OPERATIONS
//============

// ADDITION

        /**
        * Returns the addition of this matrix and another.
        * @param m a int matrix
        * @exception MatrixDimensionException If the matrices are different sizes.
        */
        public AbstractIntegerSquareMatrix add(final AbstractIntegerSquareMatrix m) {
                if(m instanceof IntegerTridiagonalMatrix)
                        return add((IntegerTridiagonalMatrix)m);
                if(m instanceof TridiagonalMatrix)
                        return addTridiagonal(m);
                if(m instanceof IntegerSquareMatrix)
                        return add((IntegerSquareMatrix)m);

                if(numRows==m.rows() && numCols==m.columns()) {
                        final int array[][]=new int[numRows][numCols];
                        for(int i=0;i<numRows;i++) {
                                array[i][0]=getElement(i,0)+m.getElement(i,0);
                                for(int j=1;j<numCols;j++)
                                        array[i][j]=getElement(i,j)+m.getElement(i,j);
                        }
                        return new IntegerSquareMatrix(array);
                } else {
                        throw new MatrixDimensionException("Matrices are different sizes.");
                }
        }
        public IntegerSquareMatrix add(final IntegerSquareMatrix m) {
                if(numRows==m.numRows && numCols==m.numCols) {
                        final int array[][]=new int[numRows][numCols];
                        for(int i=0;i<numRows;i++)
                                System.arraycopy(m.matrix[i],0,array[i],0,numRows);
                        array[0][0]+=diag[0];
                        array[0][1]+=udiag[0];
                        int n=numRows-1;
                        for(int i=1;i<n;i++) {
                                array[i][i-1]+=ldiag[i];
                                array[i][i]+=diag[i];
                                array[i][i+1]+=udiag[i];
                        }
                        array[n][n-1]+=ldiag[n];
                        array[n][n]+=diag[n];
                        return new IntegerSquareMatrix(array);
                } else
                        throw new MatrixDimensionException("Matrices are different sizes.");
        }
        /**
        * Returns the addition of this matrix and another.
        * @param m a int tridiagonal matrix
        * @exception MatrixDimensionException If the matrices are different sizes.
        */
        public IntegerTridiagonalMatrix add(final IntegerTridiagonalMatrix m) {
                int mRow=numRows;
                if(mRow==m.numRows) {
                        final IntegerTridiagonalMatrix ans=new IntegerTridiagonalMatrix(mRow);
                        ans.diag[0]=diag[0]+m.diag[0];
                        ans.udiag[0]=udiag[0]+m.udiag[0];
                        mRow--;
                        for(int i=1;i<mRow;i++) {
                                ans.ldiag[i]=ldiag[i]+m.ldiag[i];
                                ans.diag[i]=diag[i]+m.diag[i];
                                ans.udiag[i]=udiag[i]+m.udiag[i];
                        }
                        ans.ldiag[mRow]=ldiag[mRow]+m.ldiag[mRow];
                        ans.diag[mRow]=diag[mRow]+m.diag[mRow];
                        return ans;
                } else
                        throw new MatrixDimensionException("Matrices are different sizes.");
        }
        private IntegerTridiagonalMatrix addTridiagonal(final AbstractIntegerSquareMatrix m) {
                int mRow=numRows;
                if(mRow==m.rows()) {
                        final IntegerTridiagonalMatrix ans=new IntegerTridiagonalMatrix(mRow);
                        ans.diag[0]=diag[0]+m.getElement(0,0);
                        ans.udiag[0]=udiag[0]+m.getElement(0,1);
                        mRow--;
                        for(int i=1;i<mRow;i++) {
                                ans.ldiag[i]=ldiag[i]+m.getElement(i,i-1);
                                ans.diag[i]=diag[i]+m.getElement(i,i);
                                ans.udiag[i]=udiag[i]+m.getElement(i,i+1);
                        }
                        ans.ldiag[mRow]=ldiag[mRow]+m.getElement(mRow,mRow-1);
                        ans.diag[mRow]=diag[mRow]+m.getElement(mRow,mRow);
                        return ans;
                } else {
                        throw new MatrixDimensionException("Matrices are different sizes.");
                }
        }

// SUBTRACTION

        /**
        * Returns the subtraction of this matrix by another.
        * @param m a int matrix
        * @exception MatrixDimensionException If the matrices are different sizes.
        */
        public AbstractIntegerSquareMatrix subtract(final AbstractIntegerSquareMatrix m) {
                if(m instanceof IntegerTridiagonalMatrix)
                        return subtract((IntegerTridiagonalMatrix)m);
                if(m instanceof TridiagonalMatrix)
                        return subtractTridiagonal(m);
                if(m instanceof IntegerSquareMatrix)
                        return subtract((IntegerSquareMatrix)m);

                if(numRows==m.rows() && numCols==m.columns()) {
                        final int array[][]=new int[numRows][numCols];
                        for(int i=0;i<numRows;i++) {
                                array[i][0]=getElement(i,0)-m.getElement(i,0);
                                for(int j=1;j<numCols;j++)
                                        array[i][j]=getElement(i,j)-m.getElement(i,j);
                        }
                        return new IntegerSquareMatrix(array);
                } else {
                        throw new MatrixDimensionException("Matrices are different sizes.");
                }
        }
        public IntegerSquareMatrix subtract(final IntegerSquareMatrix m) {
                if(numRows==m.numRows && numCols==m.numCols) {
                        final int array[][]=new int[numRows][numCols];
                        for(int i=0;i<numRows;i++) {
                                array[i][0]=getElement(i,0)-m.matrix[i][0];
                                for(int j=1;j<numCols;j++)
                                        array[i][j]=getElement(i,j)-m.matrix[i][j];
                        }
                        return new IntegerSquareMatrix(array);
                } else
                        throw new MatrixDimensionException("Matrices are different sizes.");
        }
        /**
        * Returns the subtraction of this matrix and another.
        * @param m a int tridiagonal matrix
        * @exception MatrixDimensionException If the matrices are different sizes.
        */
        public IntegerTridiagonalMatrix subtract(final IntegerTridiagonalMatrix m) {
                int mRow=numRows;
                if(mRow==m.numRows) {
                        final IntegerTridiagonalMatrix ans=new IntegerTridiagonalMatrix(mRow);
                        ans.diag[0]=diag[0]-m.diag[0];
                        ans.udiag[0]=udiag[0]-m.udiag[0];
                        mRow--;
                        for(int i=1;i<mRow;i++) {
                                ans.ldiag[i]=ldiag[i]-m.ldiag[i];
                                ans.diag[i]=diag[i]-m.diag[i];
                                ans.udiag[i]=udiag[i]-m.udiag[i];
                        }
                        ans.ldiag[mRow]=ldiag[mRow]-m.ldiag[mRow];
                        ans.diag[mRow]=diag[mRow]-m.diag[mRow];
                        return ans;
                } else
                        throw new MatrixDimensionException("Matrices are different sizes.");
        }
        private IntegerTridiagonalMatrix subtractTridiagonal(final AbstractIntegerSquareMatrix m) {
                int mRow=numRows;
                if(mRow==m.rows()) {
                        final IntegerTridiagonalMatrix ans=new IntegerTridiagonalMatrix(mRow);
                        ans.diag[0]=diag[0]-m.getElement(0,0);
                        ans.udiag[0]=udiag[0]-m.getElement(0,1);
                        mRow--;
                        for(int i=1;i<mRow;i++) {
                                ans.ldiag[i]=ldiag[i]-m.getElement(i,i-1);
                                ans.diag[i]=diag[i]-m.getElement(i,i);
                                ans.udiag[i]=udiag[i]-m.getElement(i,i+1);
                        }
                        ans.ldiag[mRow]=ldiag[mRow]-m.getElement(mRow,mRow-1);
                        ans.diag[mRow]=diag[mRow]-m.getElement(mRow,mRow);
                        return ans;
                } else {
                        throw new MatrixDimensionException("Matrices are different sizes.");
                }
        }

// SCALAR MULTIPLICATION

        /**
        * Returns the multiplication of this matrix by a scalar.
        * @param x a int.
        * @return a int square matrix.
        */
        public AbstractIntegerMatrix scalarMultiply(final int x) {
                final IntegerTridiagonalMatrix ans=new IntegerTridiagonalMatrix(numRows);
                final int lastRow = numRows-1;
                ans.diag[0]=x*diag[0];
                ans.udiag[0]=x*udiag[0];
                for(int i=1;i<lastRow;i++) {
                        ans.ldiag[i]=x*ldiag[i];
                        ans.diag[i]=x*diag[i];
                        ans.udiag[i]=x*udiag[i];
                }
                ans.ldiag[lastRow]=x*ldiag[lastRow];
                ans.diag[lastRow]=x*diag[lastRow];
                return ans;
        }

// SCALAR DIVISON


// SCALAR PRODUCT

        /**
        * Returns the scalar product of this matrix and another.
        * @param m a int matrix.
        * @exception MatrixDimensionException If the matrices are different sizes.
        */
        public int scalarProduct(final AbstractIntegerSquareMatrix m) {
                if(m instanceof IntegerTridiagonalMatrix)
                        return scalarProduct((IntegerTridiagonalMatrix)m);
                if(m instanceof IntegerSquareMatrix)
                        return scalarProduct((IntegerSquareMatrix)m);

                if(numRows==m.rows() && numCols==m.columns()) {
                        int ans = diag[0]*m.getElement(0,0)+udiag[0]*m.getElement(0,1);
                        final int lastRow = numRows-1;
                        for(int i=1;i<lastRow;i++)
                                ans += ldiag[i]*m.getElement(i,i-1)+diag[i]*m.getElement(i,i)+udiag[i]*m.getElement(i,i+1);
                        ans += ldiag[lastRow]*m.getElement(lastRow,lastRow-1)+diag[lastRow]*m.getElement(lastRow,lastRow);
                        return ans;
                } else {
                       throw new MatrixDimensionException("Matrices are different sizes.");
                }
        }
        public int scalarProduct(final IntegerSquareMatrix m) {
                if(numRows==m.numRows && numCols==m.numCols) {
                        int ans = diag[0]*m.matrix[0][0]+udiag[0]*m.matrix[0][1];
                        final int lastRow = numRows-1;
                        for(int i=1;i<lastRow;i++)
                                ans += ldiag[i]*m.matrix[i][i-1]+diag[i]*m.matrix[i][i]+udiag[i]*m.matrix[i][i+1];
                        ans += ldiag[lastRow]*m.matrix[lastRow][lastRow-1]+diag[lastRow]*m.matrix[lastRow][lastRow];
                        return ans;
                } else
                        throw new MatrixDimensionException("Matrices are different sizes.");
        }
        public int scalarProduct(final IntegerTridiagonalMatrix m) {
                if(numRows==m.numRows) {
                        int ans = diag[0]*m.diag[0]+udiag[0]*m.udiag[0];
                        final int lastRow = numRows-1;
                        for(int i=1;i<lastRow;i++)
                                ans += ldiag[i]*m.ldiag[i]+diag[i]*m.diag[i]+udiag[i]*m.udiag[i];
                        ans += ldiag[lastRow]*m.ldiag[lastRow]+diag[lastRow]*m.diag[lastRow];
                        return ans;
                } else
                        throw new MatrixDimensionException("Matrices are different sizes.");
        }

// MATRIX MULTIPLICATION

        /**
        * Returns the multiplication of a vector by this matrix.
        * @param v a int vector.
        * @exception DimensionException If the matrix and vector are incompatible.
        */
        public AbstractIntegerVector multiply(final AbstractIntegerVector v) {
                if(numCols==v.dimension()) {
                        final int array[]=new int[numRows];
                        final int lastRow = numRows-1;
                        array[0]=diag[0]*v.getComponent(0)+udiag[0]*v.getComponent(1);
                        for(int i=1;i<lastRow;i++)
                                array[i]=ldiag[i]*v.getComponent(i-1)+diag[i]*v.getComponent(i)+udiag[i]*v.getComponent(i+1);
                        array[lastRow]=ldiag[lastRow]*v.getComponent(lastRow-1)+diag[lastRow]*v.getComponent(lastRow);
                        return new IntegerVector(array);
                } else {
                        throw new DimensionException("Matrix and vector are incompatible.");
                }
        }
        /**
        * Returns the multiplication of this matrix and another.
        * @param m a int matrix
        * @return a AbstractIntegerMatrix or a AbstractIntegerSquareMatrix as appropriate
        * @exception MatrixDimensionException If the matrices are incompatible.
        */
        public AbstractIntegerSquareMatrix multiply(final AbstractIntegerSquareMatrix m) {
                if(m instanceof IntegerTridiagonalMatrix)
                        return multiply((IntegerTridiagonalMatrix)m);
                if(m instanceof TridiagonalMatrix)
                        return multiplyTridiagonal(m);
                if(m instanceof IntegerSquareMatrix)
                        return multiply((IntegerSquareMatrix)m);

                if(numCols==m.rows()) {
                        final int mColumns = m.columns();
                        final int array[][]=new int[numRows][mColumns];
                        final int lastRow = numRows-1;
                        for(int j=0; j<numRows; j++) {
                                array[0][j]=diag[0]*m.getElement(0,j)+udiag[0]*m.getElement(1,j);
                                for(int i=1;i<lastRow;i++)
                                        array[i][j]=ldiag[i]*m.getElement(i-1,j)+diag[i]*m.getElement(i,j)+udiag[i]*m.getElement(i+1,j);
                                array[lastRow][j]=ldiag[lastRow]*m.getElement(lastRow-1,j)+diag[lastRow]*m.getElement(lastRow,j);
                        }
                        return new IntegerSquareMatrix(array);
                } else {
                        throw new MatrixDimensionException("Incompatible matrices.");
                }
        }
        public IntegerSquareMatrix multiply(final IntegerSquareMatrix m) {
                if(numCols==m.numRows) {
                        final int array[][]=new int[numRows][m.numCols];
                        final int lastRow = numRows-1;
                        for(int j=0;j<numRows;j++) {
                                array[0][j]=diag[0]*m.matrix[0][j]+udiag[0]*m.matrix[1][j];
                                for(int i=1;i<lastRow;i++)
                                        array[i][j]=ldiag[i]*m.matrix[i-1][j]+diag[i]*m.matrix[i][j]+udiag[i]*m.matrix[i+1][j];
                                array[lastRow][j]=ldiag[lastRow]*m.matrix[lastRow-1][j]+diag[lastRow]*m.matrix[lastRow][j];
                        }
                        return new IntegerSquareMatrix(array);
                } else
                        throw new MatrixDimensionException("Incompatible matrices.");
        }
        public IntegerSquareMatrix multiply(final IntegerTridiagonalMatrix m) {
                int mRow=numRows;
                if(numCols==m.numRows) {
                        final int array[][]=new int[mRow][mRow];
                        array[0][0]=diag[0]*m.diag[0]+udiag[0]*m.ldiag[1];
                        array[0][1]=diag[0]*m.udiag[0]+udiag[0]*m.diag[1];
                        array[0][2]=udiag[0]*m.udiag[1];
                        if(mRow>3) {
                                array[1][0]=ldiag[1]*m.diag[0]+diag[1]*m.ldiag[1];
                                array[1][1]=ldiag[1]*m.udiag[0]+diag[1]*m.diag[1]+udiag[1]*m.ldiag[2];
                                array[1][2]=diag[1]*m.udiag[1]+udiag[1]*m.diag[2];
                                array[1][3]=udiag[1]*m.udiag[2];
                        }
                        if(mRow==3) {
                                array[1][0]=ldiag[1]*m.diag[0]+diag[1]*m.ldiag[1];
                                array[1][1]=ldiag[1]*m.udiag[0]+diag[1]*m.diag[1]+udiag[1]*m.ldiag[2];
                                array[1][2]=diag[1]*m.udiag[1]+udiag[1]*m.diag[2];
                        } else if(mRow>4) {
                                for(int i=2;i<mRow-2;i++) {
                                        array[i][i-2]=ldiag[i]*m.ldiag[i-1];
                                        array[i][i-1]=ldiag[i]*m.diag[i-1]+diag[i]*m.ldiag[i];
                                        array[i][i]=ldiag[i]*m.udiag[i-1]+diag[i]*m.diag[i]+udiag[i]*m.ldiag[i+1];
                                        array[i][i+1]=diag[i]*m.udiag[i]+udiag[i]*m.diag[i+1];
                                        array[i][i+2]=udiag[i]*m.udiag[i+1];
                                }
                        }
                        if(mRow>3) {
                                array[mRow-2][mRow-4]=ldiag[mRow-2]*m.ldiag[mRow-3];
                                array[mRow-2][mRow-3]=ldiag[mRow-2]*m.diag[mRow-3]+diag[mRow-2]*m.ldiag[mRow-2];
                                array[mRow-2][mRow-2]=ldiag[mRow-2]*m.udiag[mRow-3]+diag[mRow-2]*m.diag[mRow-2]+udiag[mRow-2]*m.ldiag[mRow-1];
                                array[mRow-2][mRow-1]=diag[mRow-2]*m.udiag[mRow-2]+udiag[mRow-2]*m.diag[mRow-1];
                        }
                        mRow--;
                        array[mRow][mRow-2]=ldiag[mRow]*m.ldiag[mRow-1];
                        array[mRow][mRow-1]=ldiag[mRow]*m.diag[mRow-1]+diag[mRow]*m.ldiag[mRow];
                        array[mRow][mRow]=ldiag[mRow]*m.udiag[mRow-1]+diag[mRow]*m.diag[mRow];
                        return new IntegerSquareMatrix(array);
                } else
                        throw new MatrixDimensionException("Incompatible matrices.");
        }
        private IntegerSquareMatrix multiplyTridiagonal(final AbstractIntegerSquareMatrix m) {
                int mRow=numRows;
                if(numCols==m.rows()) {
                        final int array[][]=new int[mRow][mRow];
                        array[0][0]=diag[0]*m.getElement(0,0)+udiag[0]*m.getElement(1,0);
                        array[0][1]=diag[0]*m.getElement(0,1)+udiag[0]*m.getElement(1,1);
                        array[0][2]=udiag[0]*m.getElement(1,2);
                        if(mRow>3) {
                                array[1][0]=ldiag[1]*m.getElement(0,0)+diag[1]*m.getElement(1,0);
                                array[1][1]=ldiag[1]*m.getElement(0,1)+diag[1]*m.getElement(1,1)+udiag[1]*m.getElement(2,1);
                                array[1][2]=diag[1]*m.getElement(1,2)+udiag[1]*m.getElement(2,2);
                                array[1][3]=udiag[1]*m.getElement(2,3);
                        }
                        if(mRow==3) {
                                array[1][0]=ldiag[1]*m.getElement(0,0)+diag[1]*m.getElement(1,0);
                                array[1][1]=ldiag[1]*m.getElement(0,1)+diag[1]*m.getElement(1,1)+udiag[1]*m.getElement(2,1);
                                array[1][2]=diag[1]*m.getElement(1,2)+udiag[1]*m.getElement(2,2);
                        } else if(mRow>4) {
                                for(int i=2;i<mRow-2;i++) {
                                        array[i][i-2]=ldiag[i]*m.getElement(i-1,i-2);
                                        array[i][i-1]=ldiag[i]*m.getElement(i-1,i-1)+diag[i]*m.getElement(i,i-1);
                                        array[i][i]=ldiag[i]*m.getElement(i-1,i)+diag[i]*m.getElement(i,i)+udiag[i]*m.getElement(i+1,i);
                                        array[i][i+1]=diag[i]*m.getElement(i,i+1)+udiag[i]*m.getElement(i+1,i+1);
                                        array[i][i+2]=udiag[i]*m.getElement(i+1,i+2);
                                }
                        }
                        if(mRow>3) {
                                array[mRow-2][mRow-4]=ldiag[mRow-2]*m.getElement(mRow-3,mRow-4);
                                array[mRow-2][mRow-3]=ldiag[mRow-2]*m.getElement(mRow-3,mRow-3)+diag[mRow-2]*m.getElement(mRow-2,mRow-3);
                                array[mRow-2][mRow-2]=ldiag[mRow-2]*m.getElement(mRow-3,mRow-2)+diag[mRow-2]*m.getElement(mRow-2,mRow-2)+udiag[mRow-2]*m.getElement(mRow-1,mRow-2);
                                array[mRow-2][mRow-1]=diag[mRow-2]*m.getElement(mRow-2,mRow-1)+udiag[mRow-2]*m.getElement(mRow-1,mRow-1);
                        }
                        mRow--;
                        array[mRow][mRow-2]=ldiag[mRow]*m.getElement(mRow-1,mRow-2);
                        array[mRow][mRow-1]=ldiag[mRow]*m.getElement(mRow-1,mRow-1)+diag[mRow]*m.getElement(mRow,mRow-1);
                        array[mRow][mRow]=ldiag[mRow]*m.getElement(mRow-1,mRow)+diag[mRow]*m.getElement(mRow,mRow);
                        return new IntegerSquareMatrix(array);
                } else {
                        throw new MatrixDimensionException("Incompatible matrices.");
                }
        }

// TRANSPOSE

        /**
        * Returns the transpose of this matrix.
        * @return a int matrix
        */
        public Matrix transpose() {
                final IntegerTridiagonalMatrix ans=new IntegerTridiagonalMatrix(numRows);
                System.arraycopy(ldiag,1,ans.udiag,0,ldiag.length-1);
                System.arraycopy(diag,0,ans.diag,0,diag.length);
                System.arraycopy(udiag,0,ans.ldiag,1,udiag.length-1);
                return ans;
        }

// CHOLESKY DECOMPOSITION

        /**
        * Returns the Cholesky decomposition of this matrix.
        * Matrix must be symmetric and positive definite.
        * @return an array with [0] containing the L-matrix and [1] containing the U-matrix.
        */
        public AbstractDoubleSquareMatrix[] choleskyDecompose() {
                final int N=numRows;
                final double arrayL[][]=new double[N][N];
                final double arrayU[][]=new double[N][N];
                double tmp=Math.sqrt(diag[0]);
                arrayL[0][0]=arrayU[0][0]=tmp;
                arrayL[1][0]=arrayU[0][1]=ldiag[1]/tmp;
                for(int j=1;j<N;j++) {
                        tmp=diag[j];
                        for(int i=0;i<j;i++)
                                tmp-=arrayL[j][i]*arrayL[j][i];
                        arrayL[j][j]=arrayU[j][j]=Math.sqrt(tmp);
                        if(j+1<N) {
                                tmp=ldiag[j+1];
                                for(int k=0;k<j+1;k++)
                                        tmp-=arrayL[j][k]*arrayU[k][j+1];
                                arrayL[j+1][j]=arrayU[j][j+1]=tmp/arrayU[j][j];
                                for(int i=j+2;i<N;i++) {
                                        tmp=0.0;
                                        for(int k=0;k<i;k++)
                                                tmp-=arrayL[j][k]*arrayU[k][i];
                                        arrayL[i][j]=arrayU[j][i]=tmp/arrayU[j][j];
                                }
                        }
                }
                final AbstractDoubleSquareMatrix lu[]=new AbstractDoubleSquareMatrix[2];
                lu[0]=new DoubleSquareMatrix(arrayL);
                lu[1]=new DoubleSquareMatrix(arrayU);
                return lu;
        }

// QR DECOMPOSITION

        /**
        * Returns the QR decomposition of this matrix.
        * Based on the code from <a href="http://math.nist.gov/javanumerics/jama/">JAMA</a> (public domain).
        * @return an array with [0] containing the Q-matrix and [1] containing the R-matrix.
        * @jsci.planetmath QRDecomposition
        */
        public AbstractDoubleSquareMatrix[] qrDecompose() {
                final int N=numRows;
                final double array[][]=new double[N][N];
                final double arrayQ[][]=new double[N][N];
                final double arrayR[][]=new double[N][N];
                // copy matrix
                array[0][0]=diag[0];
                array[0][1]=udiag[0];
                for(int i=1;i<N-1;i++) {
                        array[i][i-1]=ldiag[i];
                        array[i][i]=diag[i];
                        array[i][i+1]=udiag[i];
                }
                array[N-1][N-2]=ldiag[N-1];
                array[N-1][N-1]=diag[N-1];
                for(int k=0; k<N; k++) {
                        // compute l2-norm of kth column
                        double norm = array[k][k];
                        for(int i=k+1; i<N; i++)
                                norm = ExtraMath.hypot(norm, array[i][k]);
                        if(norm != 0.0) {
                                // form kth Householder vector
                                if(array[k][k] < 0.0)
                                        norm = -norm;
                                for(int i=k; i<N; i++)
                                        array[i][k] /= norm;
                                array[k][k] += 1.0;
                                // apply transformation to remaining columns
                                for(int j=k+1; j<N; j++) {
                                        double s=array[k][k]*array[k][j];
                                        for(int i=k+1; i<N; i++)
                                                s += array[i][k]*array[i][j];
                                        s /= array[k][k];
                                        for(int i=k; i<N; i++)
                                                array[i][j] -= s*array[i][k];
                                }
                        }
                        arrayR[k][k] = -norm;
                }
                for(int k=N-1; k>=0; k--) {
                        arrayQ[k][k] = 1.0;
                        for(int j=k; j<N; j++) {
                                if(array[k][k] != 0.0) {
                                        double s = array[k][k]*arrayQ[k][j];
                                        for(int i=k+1; i<N; i++)
                                                s += array[i][k]*arrayQ[i][j];
                                        s /= array[k][k];
                                        for(int i=k; i<N; i++)
                                                arrayQ[i][j] -= s*array[i][k];
                                }
                        }
                }
                for(int i=0; i<N; i++) {
                        for(int j=i+1; j<N; j++)
                                arrayR[i][j] = array[i][j];
                }
                final AbstractDoubleSquareMatrix qr[]=new AbstractDoubleSquareMatrix[2];
                qr[0]=new DoubleSquareMatrix(arrayQ);
                qr[1]=new DoubleSquareMatrix(arrayR);
                return qr;
        }

// SINGULAR VALUE DECOMPOSITION

        /**
        * Returns the singular value decomposition of this matrix.
        * Based on the code from <a href="http://math.nist.gov/javanumerics/jama/">JAMA</a> (public domain).
        * @return an array with [0] containing the U-matrix, [1] containing the S-matrix and [2] containing the V-matrix.
        */
        public AbstractDoubleSquareMatrix[] singularValueDecompose() {
                final int N=numRows;
                final int Nm1=N-1;
                final double array[][]=new double[N][N];
                final double arrayU[][]=new double[N][N];
                final double arrayS[]=new double[N];
                final double arrayV[][]=new double[N][N];
                final double e[]=new double[N];
                final double work[]=new double[N];
                // copy matrix
                array[0][0]=diag[0];
                array[0][1]=udiag[0];
                for(int i=1;i<Nm1;i++) {
                        array[i][i-1]=ldiag[i];
                        array[i][i]=diag[i];
                        array[i][i+1]=udiag[i];
                }
                array[Nm1][Nm1-1]=ldiag[Nm1];
                array[Nm1][Nm1]=diag[Nm1];
                // reduce matrix to bidiagonal form
                for(int k=0;k<Nm1;k++) {
                        // compute the transformation for the kth column
                        // compute l2-norm of kth column
                        arrayS[k]=array[k][k];
                        for(int i=k+1;i<N;i++)
                                arrayS[k]=ExtraMath.hypot(arrayS[k],array[i][k]);
                        if(arrayS[k]!=0.0) {
                                if(array[k][k]<0.0)
                                        arrayS[k]=-arrayS[k];
                                for(int i=k;i<N;i++)
                                        array[i][k]/=arrayS[k];
                                array[k][k]+=1.0;
                        }
                        arrayS[k]=-arrayS[k];
                        for(int j=k+1;j<N;j++) {
                                if(arrayS[k]!=0.0) {
                                        // apply the transformation
                                        double t=array[k][k]*array[k][j];
                                        for(int i=k+1; i<N; i++)
                                                t+=array[i][k]*array[i][j];
                                        t /= array[k][k];
                                        for(int i=k; i<N; i++)
                                                array[i][j] -= t*array[i][k];
                                }
                                e[j]=array[k][j];
                        }
                        for(int i=k;i<N;i++)
                                arrayU[i][k]=array[i][k];
                        if(k<N-2) {
                                // compute the kth row transformation
                                // compute l2-norm of kth column
                                e[k]=e[k+1];
                                for(int i=k+2;i<N;i++)
                                        e[k]=ExtraMath.hypot(e[k],e[i]);
                                if(e[k]!=0.0) {
                                        if(e[k+1]<0.0)
                                                e[k]=-e[k];
                                        for(int i=k+1;i<N;i++)
                                                e[i]/=e[k];
                                        e[k+1]+=1.0;
                                }
                                e[k]=-e[k];
                                if(e[k]!=0.0) {
                                        // apply the transformation
                                        for(int i=k+1;i<N;i++) {
                                                work[i]=0.0;
                                                for(int j=k+1;j<N;j++)
                                                        work[i]+=e[j]*array[i][j];
                                        }
                                        for(int j=k+1;j<N;j++) {
                                                double t = e[j]/e[k+1];
                                                for(int i=k+1;i<N;i++)
                                                        array[i][j] -= t*work[i];
                                        }
                                }
                                for(int i=k+1;i<N;i++)
                                        arrayV[i][k]=e[i];
                        }
                }
                // setup the final bidiagonal matrix of order p
                int p=N;
                arrayS[Nm1]=array[Nm1][Nm1];
                e[N-2]=array[N-2][Nm1];
                e[Nm1]=0.0;
                arrayU[Nm1][Nm1]=1.0;
                for(int k=N-2;k>=0;k--) {
                        if(arrayS[k]!=0.0) {
                                for(int j=k+1;j<N;j++) {
                                        double t=arrayU[k][k]*arrayU[k][j];
                                        for(int i=k+1;i<N;i++)
                                                t+=arrayU[i][k]*arrayU[i][j];
                                        t /= arrayU[k][k];
                                        for(int i=k;i<N;i++)
                                                arrayU[i][j] -= t*arrayU[i][k];
                                }
                                for(int i=k;i<N;i++)
                                        arrayU[i][k]=-arrayU[i][k];
                                arrayU[k][k]+=1.0;
                                for(int i=0;i<k-1;i++)
                                        arrayU[i][k]=0.0;
                        } else {
                                for(int i=0;i<N;i++)
                                        arrayU[i][k]=0.0;
                                arrayU[k][k]=1.0;
                        }
                }
                for(int k=Nm1;k>=0;k--) {
                        if(k<N-2 && e[k]!=0.0) {
                                for(int j=k+1;j<N;j++) {
                                        double t=arrayV[k+1][k]*arrayV[k+1][j];
                                        for(int i=k+2;i<N;i++)
                                                t+=arrayV[i][k]*arrayV[i][j];
                                        t /= arrayV[k+1][k];
                                        for(int i=k+1;i<N;i++)
                                                arrayV[i][j] -= t*arrayV[i][k];
                                }
                        }
                        for(int i=0;i<N;i++)
                                arrayV[i][k]=0.0;
                        arrayV[k][k]=1.0;
                }
                final double eps=Math.pow(2.0,-52.0);
                int iter=0;
                while(p>0) {
                        int k, action;
                        // action = 1 if arrayS[p] and e[k-1] are negligible and k<p
                        // action = 2 if arrayS[k] is negligible and k<p
                        // action = 3 if e[k-1] is negligible, k<p, and arrayS[k], ..., arrayS[p] are not negligible (QR step)
                        // action = 4 if e[p-1] is negligible (convergence)
                        for(k=p-2;k>=-1;k--) {
                                if(k==-1)
                                        break;
                                if(Math.abs(e[k])<=eps*(Math.abs(arrayS[k])+Math.abs(arrayS[k+1]))) {
                                        e[k]=0.0;
                                        break;
                                }
                        }
                        if(k==p-2) {
                                action=4;
                        } else {
                                int ks;
                                for(ks=p-1;ks>=k;ks--) {
                                        if(ks==k)
                                                break;
                                        double t=(ks!=p ? Math.abs(e[ks]) : 0.0)+(ks!=k+1 ? Math.abs(e[ks-1]) : 0.0);
                                        if(Math.abs(arrayS[ks])<=eps*t) {
                                                arrayS[ks]=0.0;
                                                break;
                                        }
                                }
                                if(ks==k) {
                                        action=3;
                                } else if(ks==p-1) {
                                        action=1;
                                } else {
                                        action=2;
                                        k=ks;
                                }
                        }
                        k++;
                        switch(action) {
                                // deflate negligible arrayS[p]
                                case 1: {
                                        double f=e[p-2];
                                        e[p-2]=0.0;
                                        for(int j=p-2;j>=k;j--) {
                                                double t=ExtraMath.hypot(arrayS[j],f);
                                                final double cs=arrayS[j]/t;
                                                final double sn=f/t;
                                                arrayS[j]=t;
                                                if(j!=k) {
                                                        f=-sn*e[j-1];
                                                        e[j-1]*=cs;
                                                }
                                                for(int i=0;i<N;i++) {
                                                        t=cs*arrayV[i][j]+sn*arrayV[i][p-1];
                                                        arrayV[i][p-1]=-sn*arrayV[i][j]+cs*arrayV[i][p-1];
                                                        arrayV[i][j]=t;
                                                }
                                        }
                                        } break;
                                // split at negligible arrayS[k]
                                case 2: {
                                        double f=e[k-1];
                                        e[k-1]=0.0;
                                        for(int j=k;j<p;j++) {
                                                double t=ExtraMath.hypot(arrayS[j],f);
                                                final double cs=arrayS[j]/t;
                                                final double sn=f/t;
                                                arrayS[j]=t;
                                                f=-sn*e[j];
                                                e[j]*=cs;
                                                for(int i=0;i<N;i++) {
                                                        t=cs*arrayU[i][j]+sn*arrayU[i][k-1];
                                                        arrayU[i][k-1]=-sn*arrayU[i][j]+cs*arrayU[i][k-1];
                                                        arrayU[i][j]=t;
                                                }
                                        }
                                        } break;
                                // perform one QR step
                                case 3: {
                                        // calculate the shift
                                        final double scale=Math.max(Math.max(Math.max(Math.max(
                                                Math.abs(arrayS[p-1]),Math.abs(arrayS[p-2])),Math.abs(e[p-2])),
                                                Math.abs(arrayS[k])),Math.abs(e[k]));
                                        double sp=arrayS[p-1]/scale;
                                        double spm1=arrayS[p-2]/scale;
                                        double epm1=e[p-2]/scale;
                                        double sk=arrayS[k]/scale;
                                        double ek=e[k]/scale;
                                        double b=((spm1+sp)*(spm1-sp)+epm1*epm1)/2.0;
                                        double c=(sp*epm1)*(sp*epm1);
                                        double shift=0.0;
                                        if(b!=0.0 || c!=0.0) {
                                                shift=Math.sqrt(b*b+c);
                                                if(b<0.0)
                                                        shift=-shift;
                                                shift=c/(b+shift);
                                        }
                                        double f=(sk+sp)*(sk-sp)+shift;
                                        double g=sk*ek;
                                        // chase zeros
                                        for(int j=k;j<p-1;j++) {
                                                double t=ExtraMath.hypot(f,g);
                                                double cs=f/t;
                                                double sn=g/t;
                                                if(j!=k)
                                                        e[j-1]=t;
                                                f=cs*arrayS[j]+sn*e[j];
                                                e[j]=cs*e[j]-sn*arrayS[j];
                                                g=sn*arrayS[j+1];
                                                arrayS[j+1]*=cs;
                                                for(int i=0;i<N;i++) {
                                                        t=cs*arrayV[i][j]+sn*arrayV[i][j+1];
                                                        arrayV[i][j+1]=-sn*arrayV[i][j]+cs*arrayV[i][j+1];
                                                        arrayV[i][j]=t;
                                                }
                                                t=ExtraMath.hypot(f,g);
                                                cs=f/t;
                                                sn=g/t;
                                                arrayS[j]=t;
                                                f=cs*e[j]+sn*arrayS[j+1];
                                                arrayS[j+1]=-sn*e[j]+cs*arrayS[j+1];
                                                g=sn*e[j+1];
                                                e[j+1]*=cs;
                                                if(j<Nm1) {
                                                        for(int i=0;i<N;i++) {
                                                                t=cs*arrayU[i][j]+sn*arrayU[i][j+1];
                                                                arrayU[i][j+1]=-sn*arrayU[i][j]+cs*arrayU[i][j+1];
                                                                arrayU[i][j]=t;
                                                        }
                                                }
                                        }
                                        e[p-2]=f;
                                        iter++;
                                        } break;
                                // convergence
                                case 4: {
                                        // make the singular values positive
                                        if(arrayS[k]<=0.0) {
                                                arrayS[k]=-arrayS[k];
                                                for(int i=0;i<p;i++)
                                                        arrayV[i][k]=-arrayV[i][k];
                                        }
                                        // order the singular values
                                        while(k<p-1) {
                                                if(arrayS[k]>=arrayS[k+1])
                                                        break;
                                                double tmp=arrayS[k];
                                                arrayS[k]=arrayS[k+1];
                                                arrayS[k+1]=tmp;
                                                if(k<Nm1) {
                                                        for(int i=0;i<N;i++) {
                                                                tmp=arrayU[i][k+1];
                                                                arrayU[i][k+1]=arrayU[i][k];
                                                                arrayU[i][k]=tmp;
                                                                tmp=arrayV[i][k+1];
                                                                arrayV[i][k+1]=arrayV[i][k];
                                                                arrayV[i][k]=tmp;
                                                        }
                                                }
                                                k++;
                                        }
                                        iter=0;
                                        p--;
                                        } break;
                        }
                }
                final AbstractDoubleSquareMatrix svd[]=new AbstractDoubleSquareMatrix[3];
                svd[0]=new DoubleSquareMatrix(arrayU);
                svd[1]=new DoubleDiagonalMatrix(arrayS);
                svd[2]=new DoubleSquareMatrix(arrayV);
                return svd;
        }

}
