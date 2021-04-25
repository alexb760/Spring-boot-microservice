FROM consul:1.9.5
COPY ./config-kv.json /usr/src/consul/
WORKDIR /usr/src/consul
ENV CONSUL_HTTP_ADDR=consul:8500
ENTRYPOINT until consul kv import @config-kv.json; do echo "Waiting for Consul"; sleep 2; done

