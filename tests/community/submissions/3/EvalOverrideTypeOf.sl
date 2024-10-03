//	Override TypeOf 

function main()
{
	println("Type: " + typeOf(10));
	eval("sl", "function typeOf() { return 0; }");
	println("Type: " + typeOf(10));
	
}