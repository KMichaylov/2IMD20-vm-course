# Docker setup

This folder contains a basic docker image that we will be using to run and reproduce the Toy language tests.

## Building and installing

From the _root folder_ of the git repository, run the following command to create your Docker image:

```
$ sudo docker build -f docker/Dockerfile  --progress=plain --platform=linux/amd64  -t my-ubuntu-image .
```

_Note: this command would work on Linux and on Mac OS, but on windows the command is probably different._

The `--platform=linux/amd64` flag is not needed if you are on x86 (but it is needed on Apple silicon).

Depending on your local setup, `sudo` might or might not be needed.

After building the docker image, you can run it via:

```
$ sudo docker run --platform=linux/amd64  -it my-ubuntu-image
```

This command will start the container shell:

```
root@foobar:/usr/src/vm#
```

From here, you should be able to run the tests:
```
root@foobar:/usr/src/vm# ./test_all.sh reference-impl/sl
```

The docker image contains Maven and `java`, so it should be possible to build and test your own implementation from the docker container, using `./test_all.sh`

## Using Docker for software development.

Please keep in mind that the Docker image is created when you run the `docker build` command _using a COPY of your local git repo_. This means that the image contains a snapshot of your dev folder that is taken at image generation time, but it is _not_ synchronized with your host OS. The docker image contains `git` and other basic dev tools, but has not been tested for SW development, and you might need to change the `Dockerfile` to adapt it to your needs.
