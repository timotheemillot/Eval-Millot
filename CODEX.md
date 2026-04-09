# CODEX

## Entry point

This file is the main entry point for agent work in this repository.

Any agent starting work on this project must use `CODEX.md` first to understand:

- the product scope
- the technical boundaries
- the target architecture
- the KMP expectations
- the correct orchestration path

`CODEX.md` is the operational starting document.

After reading this file, agents must continue with:

1. `EVAL.md` for evaluation expectations
2. `.codex/WORKFLOW.md` for orchestration
3. their own agent instructions in `.codex/agents`

## Mandatory execution mode

This repository is designed for a real multi-agent workflow.

When the execution environment supports sub-agents, the workflow must be executed with distinct sub-agents, not by one agent simulating every role inline.

The expected behavior is:

- one orchestration pass chooses the next role
- one dedicated sub-agent performs that role
- the result is handed off to the next sub-agent
- the cycle continues until the workflow stop conditions are met

Using only one agent to perform architecture, production, review, evaluation, and teaching in one uninterrupted pass must be treated as a degraded fallback, not as the normal path.

If a degraded fallback is used because sub-agents are unavailable or explicitly disallowed, the agent must say so clearly.

## Purpose

This file defines the functional and technical specifications of this project for AI agents working in this repository.

It is the implementation-oriented specification.

Use it to understand:

- what the application must do
- which platforms must be supported
- which architectural constraints must be respected
- which technical building blocks are mandatory
- which implementation choices are in scope or out of scope
- which agent should be involved depending on the phase of work

For evaluation strategy, grading logic, and EDUXIM alignment, also read `EVAL.md`.

## Agent bootstrap

When an agent opens this file, it must immediately determine which of these roles it is playing:

- architecture framing
- code production
- technical review
- evaluation review
- pedagogical documentation
- orchestration

Then it must use the corresponding path below.

### Quick routing

If the task is mainly about:

- architecture, KMP structure, source sets, layering:
  use `architecte-kmp`
- implementing a new slice of code:
  use `codeur-kmp`
- checking incoherence, hallucination, broken wiring, false completeness:
  use `reviewer-kmp`
- checking score-oriented conformity with the evaluation:
  use `eval-reviewer`
- documenting which EDUXIM points are really implemented:
  use `eval-teacher`
- deciding which agent should act next:
  use `orchestrateur-kmp`

### Standard execution order

Unless the task is a narrow exception, the default order is:

1. `architecte-kmp`
2. `codeur-kmp`
3. `reviewer-kmp`
4. `architecte-kmp`
5. `eval-reviewer`
6. `eval-teacher`

This order is defined in `.codex/WORKFLOW.md` and must be treated as the default orchestration path.

This order is not just conceptual.
When sub-agents are available, each step must be assigned to a distinct sub-agent aligned with the corresponding role.

### Core workflow rule

Only `codeur-kmp` should be treated as the default code-producing agent.

The other agents should normally:

- frame
- validate
- review
- prioritize
- document

They should not overlap unnecessarily.

### Mandatory reference-architecture gate

Before any substantial architecture decision, structural refactor, or new functional slice that changes layering, navigation, DI, source sets, or cross-platform integration, the workflow must include an architecture pass that uses the skill [`kmp-reference-architecture`](.codex/skills/kmp-reference-architecture/SKILL.md).

This comparison with the reference repository is mandatory for:

- architecture framing
- structural refactors
- introduction of `Presentation / Domain / Data` boundaries
- navigation design
- DI design
- adoption or migration to `Koin`
- adoption or migration to screen `ViewModel`
- `expect / actual` introduction
- platform-boundary decisions

The reference project must be used as an architectural benchmark, not as a source to copy.

## Required companion documents

This file works together with:

- `EVAL.md` for the school subject and evaluation framing
- `.codex/WORKFLOW.md` for agent sequencing
- `.codex/agents/*` for role-specific instructions
- `.codex/skills/*` for local supporting procedures

If one of these is missing, the workflow is degraded and the agent should say so explicitly.

## Project identity

- Project type: Kotlin Multiplatform application
- Recommended targets: Android and Desktop
- Evaluation subject: Rick and Morty Locations
- Main data source: Rick and Morty API
- Functional core: list locations and display location details

## Product goal

Build a clean, defendable KMP application that demonstrates:

- a real multi-platform setup
- a clean separation between Presentation, Domain, and Data
- a UI architecture aligned with UDF / MVI
- a coherent data flow around Rick and Morty locations
- a concrete use of cross-platform mechanisms such as `expect / actual`

The application is not expected to be feature-heavy.
It is expected to be pedagogically strong, technically coherent, and easy to defend during evaluation.

## Functional specification

### Main use case

The user can browse Rick and Morty locations and inspect the details of a selected location.

### Mandatory user flows

#### Mobile flow

On mobile, the app must provide:

- a `LocationList` screen
- a `LocationDetail` screen
- navigation from list to detail
- loading of the first page of locations
- loading of one location detail by identifier

#### Desktop flow

On Desktop, the app must provide:

- a single `master-detail` screen
- a list panel on the left
- a detail panel on the right
- an initial empty or placeholder detail state
- detail update when a location is selected from the list

### Mandatory displayed data

The detail screen must display at least:

- `name`
- `type`
- `dimension`
- one derived or exploited piece of information based on `residents`

Examples of acceptable resident-derived information:

- resident count
- empty/non-empty resident state
- a text derived from the number of residents

### Allowed but non-priority extensions

The following are optional:

- pagination
- refresh
- retry UX
- favorites
- local history
- richer design system

Optional features must never weaken architecture or delay the mandatory scope.

## Platform specification

### Minimum supported platforms

The project must support at least:

- Android
- Desktop

### Platform behavior differences

The functional content is shared, but presentation differs:

- Android uses a list screen and a detail screen
- Desktop uses a master-detail layout on one screen

The application must demonstrate an intentional distinction between:

- what belongs to shared logic
- what belongs to platform-specific adaptation

## API specification

### Remote API

Use the Rick and Morty API.

Primary endpoints in scope:

- location listing endpoint
- location detail endpoint by id

### Domain-relevant location fields

The remote location payload includes at least:

- `id`
- `name`
- `type`
- `dimension`
- `residents`
- `url`
- `created`

The application does not need to expose every raw API field to the UI.

## Required architecture

The project must follow a clean architecture mindset.

### Required layers

- `Presentation`
- `Domain`
- `Data`

### Dependency direction

The intended direction is:

- `Presentation` depends on `Domain`
- `Data` depends on `Domain`
- `Domain` depends on nothing implementation-specific from `Presentation` or `Data`

### Cross-native specialization

Platform-specific behavior must be isolated and justified.

This cross-native layer must not leak platform details into the Domain.

## Presentation specification

The Presentation layer must be structured with a UDF / MVI-style approach.

### Mandatory presentation concepts

- explicit screen models
- explicit UI state
- explicit UI actions or events
- separation between rendering and orchestration
- centralized navigation on mobile

### Expected presentation components

Typical expected concepts include:

- `LocationListScreen`
- `LocationDetailScreen`
- `LocationListUiState`
- `LocationDetailUiState`
- `LocationListAction`
- `LocationDetailAction`
- screen-specific `ViewModel`
- navigation coordinator or `Navigator`

Exact names may vary, but the responsibilities must remain clear.
For this repository, new work and structural refactors should prefer `ViewModel` rather than custom presenters.

### Presentation rules

- composables render state and emit events
- composables do not contain business logic
- screen state contains only what the UI needs to render
- navigation logic must not be scattered through unrelated files
- reusable UI pieces should be extracted when they become meaningful

### Desktop adaptation rule

Desktop must be implemented as a presentation adaptation of the same domain flow, not as a separate unrelated application.

## Domain specification

The Domain layer is the core of the application.

### Mandatory domain elements

- at least one domain `Model`
- at least one domain `Repository` interface

### Recommended domain capabilities

The Domain should be able to express concepts such as:

- list locations
- get location detail
- represent a selected location or location detail

### Domain rules

- domain models must be business-oriented, not API-oriented
- repository interfaces must be contracts, not technical implementations
- domain code must not depend on Compose, Ktor, Android, Desktop, or storage details

## Data specification

The Data layer must implement the contracts needed by the Domain.

### Mandatory data requirements

- one remote data source
- one second data source
- one data-layer repository implementation
- a fetch mechanism involving the two sources
- mapping between technical models and domain models

### Allowed second-source examples

- local cache
- local persistence
- in-memory cache
- history store
- favorites store
- fallback source

The second source must not be fake in a way that removes the need to demonstrate fetch behavior.

### Data rules

- remote DTOs stay in Data
- local entities stay in Data
- mapping logic is explicit
- repository implementation orchestrates source selection and data transformation
- Domain does not receive raw DTOs or platform-specific technical models

## Dependency injection specification

The project should use `Koin` as its DI mechanism, aligned with the reference project.

The DI setup must be:

- understandable
- scoped to real composition needs
- visible enough to defend during evaluation
- initialized explicitly on supported platforms
- organized through clear Koin modules

Expected DI direction:

- shared wiring should live in shared Koin modules
- platform-specific bindings may live in platform source sets
- screen `ViewModel` instances should be resolved through Koin rather than hand-built containers

Manual DI containers may exist temporarily during migration, but they should be treated as transitional, not as the target architecture.

DI is not a cosmetic bonus.
It should support architecture readability, wiring clarity, and consistency with the example repository.

## Cross-native specification

The project must demonstrate real Kotlin Multiplatform specialization.

### Mandatory cross-native items

- at least one meaningful `expect / actual`
- one `Manager` able to play a sound
- one `Context` extension function

### Audio manager rules

The audio manager must:

- be integrated into a real app behavior
- not exist as dead code
- be placed in a way that preserves layer boundaries

Example integration points:

- when opening a detail
- when selecting an item
- during a significant UI interaction

### Context extension rules

The `Context` extension function must:

- have a concrete utility
- stay in the Android-specific integration area
- not contaminate the Domain with Android details

### Expect/actual rules

The `expect / actual` example must:

- solve a real cross-platform need
- be understandable and defensible
- not feel artificial or decorative

## Documentation specification

The repository must contain documentation that helps an evaluator or a new developer resume the project quickly.

### Mandatory documentation goals

- explain the project structure
- explain the layer split
- explain supported platforms
- explain how to run the project
- explain major architecture choices

### Commenting rules

Comments must be:

- rare
- targeted
- useful

Good comment targets:

- complex orchestration
- non-obvious fetch strategy
- cross-platform adaptation decisions
- a complex UI state, `ViewModel`, or Koin integration component

## Naming and structure rules

- package names must be consistent
- file names must reflect responsibility
- architectural intent should be visible from the tree
- avoid dumping multiple unrelated responsibilities into one file

Recommended package families:

- `presentation`
- `domain`
- `data`
- `navigation`
- `designsystem`
- `di`
- `platform` or another clear native-integration area

When Koin is introduced, module files should make architectural intent obvious from the tree.

These names are recommendations, not rigid requirements, but the resulting architecture must remain obvious.

## Non-functional requirements

### Buildability

- the project must compile
- the app must remain runnable
- incomplete work should be stubbed cleanly rather than left broken

### Defensibility

- architecture choices must be easy to explain
- code should be readable under time pressure
- the repository should look intentional rather than improvised

### Maintainability

- avoid duplication where extraction is justified
- keep responsibilities narrow
- favor explicit flows over hidden magic

## Out of scope

Unless explicitly needed later, the following are not primary goals:

- exhaustive feature set
- complex offline-first system
- full Apple support
- advanced animations
- polished production-grade visual design
- broad API coverage beyond the location use case

## Delivery priorities

When tradeoffs are necessary, prioritize implementation in this order:

1. build stability
2. clean project structure
3. domain contracts
4. data flow with two sources
5. mobile list/detail navigation
6. desktop master-detail adaptation
7. UDF / MVI clarity
8. cross-native audio manager and `expect / actual`
9. secondary UX improvements

## Expected repository evolution

The repository currently starts from a Compose Multiplatform template.

Agents should evolve it toward:

- an evaluation-ready KMP application
- a visible clean architecture
- a defendable functional scope around Rick and Morty locations
- a `Koin`-based DI setup
- a `ViewModel`-based presentation layer inspired by the reference project
- a structure that directly supports the EDUXIM grid
- a repository that can be safely traversed by the orchestration workflow

## Agent instructions

When modifying the project:

1. read `CODEX.md`
2. identify the correct agent role from the routing section above
3. read `EVAL.md`
4. read `.codex/WORKFLOW.md`
5. if the task is structural or starts a new slice, route through `architecte-kmp` first
6. use distinct sub-agents when the environment supports them
7. preserve buildability
8. implement or review only within your role boundaries
9. implement the smallest coherent slice that demonstrates a real criterion
10. avoid superficial complexity
11. keep the architecture explainable
12. when touching presentation or DI, prefer `ViewModel + Koin` over presenters and manual wiring

When unsure between two implementations, prefer the one that is:

- easier to defend
- clearer to review
- more aligned with clean architecture
- more useful for EDUXIM demonstration

## Agent handoff rule

When your role is complete, do not continue into another agent's role implicitly.

Instead, hand off to the correct next agent according to `.codex/WORKFLOW.md`.

Examples:

- after implementation, hand off to `reviewer-kmp`
- after technical review, hand off either to `codeur-kmp` or `architecte-kmp`
- after evaluation review, hand off to `eval-teacher` only if the implementation is real enough to document

This repository should behave like a disciplined multi-agent pipeline, not like a single agent doing every role at once.

When sub-agents are available, "hand off" means actually delegating to the next dedicated sub-agent rather than merely narrating what that agent would have done.
