# Use Ubuntu 22.04 as the base image
FROM ubuntu:22.04

# Set the environment variables for non-interactive installation
ENV DEBIAN_FRONTEND=noninteractive

# Install dependencies
RUN apt-get update && apt-get install -y \
    wget \
    curl \
    unzip \
    git \
    ca-certificates \
    build-essential \
    software-properties-common \
    && rm -rf /var/lib/apt/lists/*

# Install Maven 3.9.9
RUN wget https://archive.apache.org/dist/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz \
    && tar xzf apache-maven-3.9.9-bin.tar.gz \
    && mv apache-maven-3.9.9 /opt/maven \
    && rm apache-maven-3.9.9-bin.tar.gz

# Set MAVEN_HOME and update PATH
ENV MAVEN_HOME=/opt/maven
ENV PATH=$MAVEN_HOME/bin:$PATH

# Install GraalVM CE 21 (OpenJDK 21) from GitHub release
RUN wget https://github.com/graalvm/graalvm-ce-builds/releases/download/jdk-21.0.0/graalvm-community-jdk-21.0.0_linux-x64_bin.tar.gz
RUN tar -xzf graalvm-community-jdk-21.0.0_linux-x64_bin.tar.gz
RUN mv graalvm-community-openjdk-21+35.1 /opt/graalvm
RUN rm graalvm-community-jdk-21.0.0_linux-x64_bin.tar.gz

# Set JAVA_HOME and update PATH
ENV JAVA_HOME=/opt/graalvm
ENV PATH=$JAVA_HOME/bin:$PATH

# Copy the VM project
COPY . /usr/src/vm

# Set the working directory
WORKDIR /usr/src/vm

# Display versions to verify installation
RUN java -version && mvn -version

RUN ls -al .
RUN cd reference-impl && mvn clean package

CMD ["bash"]
