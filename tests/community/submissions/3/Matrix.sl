//	Matrix

function CreateMatrix(m, n)
{
	matrix = new();
	i = 0;
	while (i < m)
	{
		matrix[i] = new();
		j = 0;
		while (j < n)
		{
			matrix[i][j] = 0;
			j = j + 1;
		}
		
		i = i + 1;
	}
	
	matrix.m = m;
	matrix.n = n;
	
	return matrix;
}

function Matrix_Add(lhs, rhs)
{
	if (lhs.m != rhs.m || lhs.n != rhs.n)
	{
		println("Invalid matrix sizes");
		return;
	}
	
	output = CreateMatrix(lhs.m, lhs.n);
	
	i = 0;
	while (i < lhs.m)
	{
		j = 0;
		while (j < lhs.n)
		{
			output[i][j] = lhs[i][j] + rhs[i][j];
			j = j + 1;
		}
		i = i + 1;
	}
	
	return output;
}

function Matrix_Format(matrix)
{
	output = "";
	
	i = 0;
	while (i < matrix.m)
	{
		j = 0;
		output = output + "[";
		while (j < matrix.n)
		{
			output = output + matrix[i][j];
			output = output + ", ";
			j = j + 1;
		}
		output = output + "]";
		i = i + 1;
	}
		
	return output;
}

function main()
{
	a = CreateMatrix(3, 3);
	a[0][0] = 1;
	a[0][1] = 2;
	a[0][2] = 3;
	
	a[1][0] = 4;
	a[1][1] = 5;
	a[1][2] = 6;
	
	a[2][0] = 7;
	a[2][1] = 8;
	a[2][2] = 9;
	
	b = CreateMatrix(3, 3);
	b[0][0] = 1;
	b[0][1] = 2;
	b[0][2] = 3;
	
	b[1][0] = 4;
	b[1][1] = 5;
	b[1][2] = 6;
	
	b[2][0] = 7;
	b[2][1] = 8;
	b[2][2] = 9;
	
	c = Matrix_Add(a, b);
	println(Matrix_Format(c));
	
	c = Matrix_Add(a, c);
	println(Matrix_Format(c));
	
	c = Matrix_Add(c, c);
	println(Matrix_Format(c));

}
