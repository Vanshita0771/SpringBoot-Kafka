# SpringBoot-Kafka
🔹 Order Service (Producer)
Handles incoming customer orders and publishes order events to Kafka. It’s the entry point of the flow and responsible for initiating the order lifecycle.

🔹 Inventory Service (Consumer + Producer)
Consumes the order events to verify stock availability. If inventory is sufficient, it updates stock levels and produces a new event(inventory) indicating the result. This service plays a dual role—validating and updating.

🔹 Payment Service (Consumer)
Listens for inventory confirmation events and processes payment accordingly. If everything checks out, it process the transaction securely.

All services are built as independent Spring Boot microservices, communicating asynchronously via Kafka topics. This architecture allows each service to scale and evolve independently, while still staying in sync through event streams.
