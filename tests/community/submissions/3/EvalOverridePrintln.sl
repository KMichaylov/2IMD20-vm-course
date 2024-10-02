//	Override println

function main()
{
	println("Overriding println function");
	eval("sl", "function println() {}");
	println("I don't print anything anymore");
}
