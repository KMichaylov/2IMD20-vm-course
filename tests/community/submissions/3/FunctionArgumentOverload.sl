//	Overloading a function with less or more arguments

function D1(a)
{
	println(a);
}

function D2(a, b)
{
	println("D2 Test:");
	println("Parameter A: " + a);
	println("Parameter B: " + b);
	return a == b;
}

function main()
{
	println("Hello", "World");
	println();
	
	D1(100);
	D1(10);
	D1();
	
	asd = 1234;
	
	println(D2(asd, asd)); // True
	println(D2(asd)); // False
	println(D2()); // True
	
	println(asd);
}