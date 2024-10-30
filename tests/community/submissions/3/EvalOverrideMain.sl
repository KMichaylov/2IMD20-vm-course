//	During main function execution, override it

function MainOverride()
{
	eval("sl", "function main() {}");

	println("Main is now gone");
}

function main()
{
	MainOverride();
	
	println("Done");
	
	// Main will not go into infinite loop
	main();
	
	println("Reachable as main is overriden");
}
