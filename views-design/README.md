# Views Design

## Content

### CollapsingToolbarLayout extensions

`contentScrimColor` allows to set the content scrim color of a
`CollapsingToolbarLayout` using property syntax.

### TextInputLayout extensions

The `text` read/write extension property for `TextInputLayout` is an alias
to the `text` property of the child `EditText`.

The `string` read only extension property for `TextInputLayout` allows to
easily get the `String` (immutable) representation of the text of the
child `EditText`.