# KMP Reference Architecture

## Purpose

This skill helps an agent inspect the reference repository located at:

`C:\Users\timot\StudioProjects\rick-and-morty-api`

Use it to compare architectural patterns with the current repository.

This skill is for architectural benchmarking, not for copying code.

For this repository, this skill is a required gate for meaningful architecture framing, not a nice-to-have.

## Primary use case

Use this skill when you need to answer questions such as:

- how should a KMP architecture be split in a more mature repository?
- how can shared code and platform-specific code be organized cleanly?
- how can feature modules and core modules coexist in a KMP project?
- what architecture ideas from the reference repo can guide this project without overfitting?

In this repository, you must use this skill before:

- proposing a new architecture direction
- validating a structural refactor
- introducing or changing `Presentation / Domain / Data` boundaries
- introducing navigation structure
- introducing DI structure
- introducing a meaningful `expect / actual`
- judging whether the current project is under-structured or over-structured

## Reference project overview

Observed high-level structure in the reference repository:

- root `composeApp`
- `core/app`
- `core/common`
- `core/data`
- `core/domain`
- `core/navigation`
- `core/presentation`
- `features/characters/*`
- `features/episodes/*`

Observed feature breakdown examples:

- `api`
- `data`
- `domain`
- `navigation`
- `presentation`

This suggests a modular architecture where:

- cross-feature concerns live in `core`
- feature-specific concerns live in `features`
- architectural responsibilities are visible in the tree

## How to use the reference repository

Study the reference project to understand:

- module boundaries
- layering strategy
- naming consistency
- separation between feature code and shared code
- placement of navigation and presentation concerns
- how a KMP repository can remain readable as it grows

Do not assume every architectural choice in the reference repo must be reproduced here.

The current repository may stay simpler if the subject scope is smaller.

The point of this skill is to force a concrete comparison before giving architectural advice.
If you did not inspect the reference repository, you did not complete the skill.

## Mandatory comparison method

When using this skill, compare the current repository and the reference project with this order:

1. compare top-level modules and package intent
2. compare the visibility of `Presentation`, `Domain`, `Data`, and navigation boundaries
3. compare KMP source set placement
4. compare how platform adaptation is isolated
5. compare whether the current repo is under-structured, over-structured, or appropriately structured for its scope

You should also answer:

6. which architectural elements are essential right now
7. which elements would be premature complexity right now

## What to extract from the reference project

You may extract architectural lessons such as:

- keeping architecture legible from the repository tree
- making shared concerns explicit
- separating feature-specific code from application-wide concerns
- isolating navigation as a first-class concern
- avoiding monolithic `commonMain` dumps

## What not to do

Do not:

- copy code blindly
- mirror the module tree one-to-one without justification
- import complexity that the current evaluation scope does not need
- claim that architectural similarity alone proves correctness

The current project is smaller and evaluation-driven.
The architecture should be strong enough to demonstrate the expected criteria, but not larger than necessary.

## Recommended inspection points

Inspect these files or zones in the reference project when relevant:

- `C:\Users\timot\StudioProjects\rick-and-morty-api\settings.gradle.kts`
- `C:\Users\timot\StudioProjects\rick-and-morty-api\composeApp`
- `C:\Users\timot\StudioProjects\rick-and-morty-api\core`
- `C:\Users\timot\StudioProjects\rick-and-morty-api\features`
- `C:\Users\timot\StudioProjects\rick-and-morty-api\README.md`

## Decision rule

Use the reference repository to answer:

- what architectural pattern is proven viable?

Then use `CODEX.md` to answer:

- what architecture is actually required here?

Always let `CODEX.md` win when the reference project is more complex than this repository needs.

But do not skip the comparison simply because you expect `CODEX.md` to win.
The comparison itself is part of the required reasoning.

## Expected output when this skill is used

A good output based on this skill should clarify:

- what architectural pattern from the reference repo is relevant
- how that pattern maps to the current project
- whether it should be adopted fully, partially, or not at all
- what the smallest correct adaptation is for this repository
- what was explicitly rejected from the reference repo and why
