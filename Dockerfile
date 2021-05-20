FROM adoptopenjdk/openjdk11


ENV LANG            en_US.UTF-8
ENV LC_ALL          en_US.UTF-8

RUN export DEBIAN_FRONTEND=noninteractive && \
    apt-get update && \
    apt-get install -y software-properties-common unzip && \
    apt-get update && \
    apt-get install -y --no-install-recommends locales && \
    locale-gen en_US.UTF-8 && \
    apt-get dist-upgrade -y

COPY target/universal/wm4sq-1.0.zip /opt/

RUN cd /opt && unzip wm4sq-1.0.zip && chmod u+x /opt/wm4sq-1.0/bin/wm4sq

EXPOSE 3339

CMD ["/opt/wm4sq-1.0/bin/wm4sq", "-J-mx250m", "-J-ms250m"]