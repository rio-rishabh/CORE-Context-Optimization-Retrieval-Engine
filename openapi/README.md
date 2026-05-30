# CORE HTTP API (OpenAPI)

Contract for the stateless CORE server used by the Chrome extension and other clients.

- **Spec:** [`core-api.yaml`](./core-api.yaml)
- **Default server:** `http://localhost:8080` (when `server` module is added)

## Layout

```
TokenOptimizationEngine/
├── openapi/          ← API contract (this folder)
├── app/              ← Java domain (ChatOrchestrator, MemoryStore, …)
└── server/           ← (planned) Spring Boot implementation
```

## Operations

| Method | Path | Maps to |
|--------|------|---------|
| `GET` | `/health` | Liveness |
| `POST` | `/conversations/{id}/turns` | `ChatOrchestrator.handleTurn` |
| `POST` | `/conversations/{id}/chat` | `ChatOrchestrator.buildContextWithMemories` |
| `DELETE` | `/conversations/{id}` | `MemoryStore.deleteConversation` |

View/edit the spec in [Swagger Editor](https://editor.swagger.io/) (paste `core-api.yaml`).
