function carStart() {
  println("Car started.");
}

function carStop() {
  println("Car stopped.");
}

function carAccelerate() {
  println("Car accelerated.");
}

function buildCar(brand, name) {
  car = new();
  car.brand = brand;
  car.name = name;
  car.start = carStart;
  car.stop = carStop;
  println(car.brand + " car named " + car.name + " was built.");
  return car;
}

function upgradeCar(car) {
  car.accelerate = carAccelerate;
}

function main() {
  a = buildCar("BMW", "A");
  b = buildCar("Mercedes", "B");
  a.start();
  a.stop();
  b.start();
  b.stop();
  upgradeCar(b);
  b.accelerate();
}

// This unit test involves creation and modification of templated
//     (or I'd rather call - TYPED, but from an easy implementation) objects.
// We create the objects from object factory functions and assign values to their properties.
// Later we apply modification functions to the object and validate the results.
