# DataSchema

DataSchema allows a user to generate classes and structural representations of their data formats, as
defined within data files.

Comect uses DataSchema internally. We initially designed it with our needs in mind, but we quickly realised that other
developers may find it useful for their own projects, so we decided to open-source it. Feel free to contribute as
required by your projects.

We designed DataSchema to ease the development of software that uses components written in many programming languages,
which share common data format definitions. For example, DataSchema is suitable for a piece of software that uses a
TypeScript front-end and a Kotlin or Python back-end.

---

# Contributions

We're happy to accept contributions from anyone who feels that they have something to bring to the DataSchema project.
If you'd like to contribute, and you're not sure where to get started, please feel free to open an issue, and we'll
give you a hand.

## New Languages

Feel free to submit a pull request if you'd like to add support for a new language. You can find language
definitions within `library/src/main/resources/org/comect/misc/dataschema/languages`, with each directory
representing a single supported language.

These directories must contain the following files:

- Code generation templates, written in the Kotlin dialect for [the JTE template engine](https://jte.gg).
	- `comment.kte` – Template used to generate comments.
	- `file.kte` – Template used to generate the file itself, including the class.
	- `variable.kte` – Template used to generate variable lines.
- `settings.json` - Settings file used to control code generation and map supported types.
	- `extension` (**string**) – File extension for generated files.
	- `name` (**string**) – Language name, matching the directory name.
	- `nullable_prefix` (**string**) – Optional string to prefix nullable types with.
	- `nullable_suffix` (**string**) – Optional string to suffix nullable types with.
	- `types` (**map**) – A mapping of supported basic types to types that the language supports.
		- This is a mapping of supported basic types to language types – for example, in Python, `MutableMap` would map
		  to `dict[$1, $2]`, where `$1` and `$2` represent type parameters.
	- `imports` (**map**) – A list of imports required for specific types to work.
		- This is a mapping of supported types to import names.

For more information on how these files work, you should 
[browse through them yourself](library/src/main/resources/org/comect/misc/dataschema/languages).

---

# CLI Usage

1. Download the latest copy of DataSchema from [the Releases page](https://github.com/comect-org/DataSchema/releases)
2. Execute it using `java -jar DataSchema.jar` or via one of the provided scripts
3. Once you've generated some code, we recommend running a code formatting tool such as
   [Prettier](https://prettier.io) over it.

## Arguments

- `input` - Path to your definition file, which may be any of the supported formats.
- `output` - Path to a directory to output resulting files to.

## Options

- `--overwrite` or `-ow` (default: `true`) - whether to overwrite existing files.

## Definition Files

Definition files are simply data files containing information about your data structures, which will ultimately be
used to generate data structures in supported languages. For an example, take a look at [example.yml](`example.yml`)
in this repository.

### Supported Formats

Definition files may be any of the following formats:

- JSON (ending in `.json`)
- JSON5 (ending in `.json5`)
- TOML (ending in `.toml`)
- XML (ending in `.xml`)
- YAML (ending in `.yml` or `.yaml`)

### Structure

You must structure definition files as follows:

- `types` (**list**) — List of types to generate.
	- `comment` (**string**) — An optional comment to annotate the class with.
	- `name` (**string**) — Name of the type, used to name both the file and class.
	- `attributes` (**list**) — A list of attributes, representing properties to generate within the class.
		- `comment` (**string**) — An optional comment to annotate the attribute with.
		- `constant` (**boolean**) — For languages that support it, whether the attribute should be a constant.
		- `name` (**string**) — Name of the attribute.
		- `type` (**string**) — A supported data type.
		- `parameters` (**list**) — For container types, a list of type parameters.
			- `parameters` (**list**) — For container types, a list of type parameters.
			- `type` (**string**) — A supported data type.
- `settings` (**map**) — Generation settings.
	- `languages` (**list**) — List of languages to generate code for.
	- `packageName` (**string**) — Package name to use, for languages that require a package definition.

### Supported Data Types

DataSchema supports the following data types for `type` specifications.
Please note that capitalisation is important, as DataSchema will assume that unknown types must be custom, and it will
include them verbatim in the generated classes.

#### Basic Types

For languages that don't support any of these types, DataSchema will use an equivalent type.
For example, Python doesn't have separate `Long` or `Double` types, but its `int` and `float` types are precise
enough to be used as alternatives.

- `Boolean`
- `Double`
- `Float`
- `Int`
- `Long`
- `String`

#### Immutable Containers

For languages that don't support immutable containers, DataSchema will use the mutable equivalent.

- `ImmutableList` — 1 type parameter
- `ImmutableMap` — 2 type parameters
- `ImmutableSet` — 1 type parameter

#### Mutable Containers

For languages that don't support mutable containers, DataSchema will use the immutable equivalent.

- `MutableList` — 1 type parameter
- `MutableMap` — 2 type parameters
- `MutableSet` — 1 type parameter

### Supported Languages

We store the supported language definitions within the resources of the `library` project.
If you like, you can [browse those files yourself](library/src/main/resources/org/comect/misc/dataschema/languages).

DataSchema supports the following languages:

- `kotlin` - Kotlin data classes with `kotlinx.serialization` support
- `python` - Python dataclasses
- `typescript-cjs` - TypeScript CommonJS module-style classes, with built-in JSON serialisation functions
- `typescript-es` - TypeScript ES module-style classes, with built-in JSON serialisation functions
