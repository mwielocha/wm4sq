FROM amd64/ubuntu:18.04

ENV JAVA_HOME       /usr/lib/jvm/jdk1.8.0_181/jre
ENV LANG            en_US.UTF-8
ENV LC_ALL          en_US.UTF-8


RUN export DEBIAN_FRONTEND=noninteractive && \
    apt-get update && \
    apt-get install -y software-properties-common unzip && \
    apt-get update && \
    apt-get install -y --no-install-recommends locales && \
    locale-gen en_US.UTF-8 && \
    apt-get dist-upgrade -y && \
    apt-get --purge remove openjdk* && \
    apt-get install -y wget && \
    wget https://s3.amazonaws.com/arazoo-dev/server-jre-8u181-linux-x64.tar.gz -P /opt && \
    mkdir -p /usr/lib/jvm/ && \
    tar xzf /opt/server-jre-8u181-linux-x64.tar.gz -C /usr/lib/jvm/ && \
    update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk1.8.0_181/jre/bin/java 10

COPY target/universal/wm4sq-1.0.zip /opt/

RUN cd /opt && unzip wm4sq-1.0.zip && chmod u+x /opt/wm4sq-1.0/bin/wm4sq

EXPOSE 3339

CMD ["/opt/wm4sq-1.0/bin/wm4sq", "-J-mx250m", "-J-ms250m"]