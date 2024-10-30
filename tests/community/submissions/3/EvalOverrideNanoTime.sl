//	Override nanoTime

function main()
{
	println("Overriding nanoTime function");
	eval("sl", "function nanoTime() {}");
	nt = nanoTime();
	println("Time: " + nt);
}
