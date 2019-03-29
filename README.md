[![CircleCI](https://circleci.com/gh/hadilq/num4k.svg?style=svg)](https://circleci.com/gh/hadilq/num4k)

Num4K
===
This library is inspired by `numpy` library, but currently just supports tensors.
The idea is to create a modularized, mathematical and multiplatform Kotlin library to use everywhere, for ever :D

It's goal is to implement tools in `Kotlin` way.
For instance, if we have
`Array<T>`, `IntArray`, `DoubleArray`, `Array<Int>.toIntArray`, `IntArray.toTypedArray`, `Array<T>.map`, `Array.forEach`, `Array.forEachIndexed`, etc. in `Kotlin` then respectively we have 
`InMemTensor<T>`, `IntTensor`, `DoubleTensor`, `Tensor<Int>.toIntTensor`, `IntTensor.toTypedTensor`, `Tensor<T>.map`, `Tensor<T>.forEach`, `Tensor<T>.forEachIndexed`, etc. in this library.

Usage
---
To create a `2D` tensor of `Double` type, just do this
```kotlin
val m = DoubleTensor.create2D {
            columns = 3
            default = 0.0
        }
```
To read and write to this tensor, just do this
```kotlin
val a = m[0, 2] // read. Notice it's the default value which is the 0.0
m[0, 1] = 2     // write
```
To create a `2D` tensor of `Int` type, just do this
```kotlin
val m = IntTensor.create2D {
            columns = 3
            default = 0.0
        }
```
To create a `2D` tensor of any type, just do this
```kotlin
val m = InMemTensor.create2D<DemoType> {
            columns = 3
            default = 0.0
        }
```
To multiply two 2D tensor, just do this
```kotlin
val m = DoubleTensor.create2D {
            rows = 4
            columns = 3
            default = 0.0
        }
m.forEachPosition { position ->
            m.set(/* the value you want to put in this position */, position)
        }
val n = DoubleTensor.create2D {
            rows = 3
            columns = 7
            default = 0.0
        }
// Fill n too
val product = m dot n // This is the multiplication of m and n matrices
```
To create a higher dimensional tensor, just do this
```kotlin
val m = DoubleTensor.create {
            dimension = intArrayOf(6, 8, 4, 7)
            default = 0.0
        }
```
To multiply two higher dimensional tensors, just do this
```kotlin
val m = DoubleTensor.create {
            dimension = intArrayOf(6, 8, 4, 7) // 4D tensor
            default = 0.0
        }
val n = DoubleTensor.create {
            dimension = intArrayOf(2, 3, 8, 4, 9) // 5D tensor
            default = 0.0
        }
// Fill m and n here
val dot = m.dot(1, 2, n) // sum over 8 elements of dimension[1] of m and dimension[2] of n
```
To transpose a 2D tensor, just do this
```kotlin
val t = InMemTensor.create2D<Int>().transpose()
```
And many other usages.

Download
---
Snapshots of the development version are available in [Sonatype's snapshots repository](https://oss.sonatype.org/content/repositories/snapshots/com/github/hadilq/num4k).

TODO
---
- Implement `OnDiskTensor`
- Add other modules for other mathematical concepts(Please if you feel one part of math concepts is more important and should implement sooner, just open an issue)


Contribution
---
Just create your branch from the master branch, change it, write additional tests, satisfy all tests, create your pull
request, thank you, you're awesome.