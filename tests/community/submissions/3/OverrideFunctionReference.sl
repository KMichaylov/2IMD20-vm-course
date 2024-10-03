//	Override Function Reference

function StandardFunction()
{
	println("Hello World");
}

function main()
{
	a = StandardFunction;
	a();
	
	eval("sl", "function StandardFunction() {}");
	a();
}