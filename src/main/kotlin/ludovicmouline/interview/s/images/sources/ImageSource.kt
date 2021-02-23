package ludovicmouline.interview.s.images.sources

import ludovicmouline.interview.s.images.Image

/**
 * Interface of all the classes that should be responsible of pulling
 * images from other REST API.
 */
interface ImageSource {
    fun pullImages(nb: Int) : Array<Image>
}