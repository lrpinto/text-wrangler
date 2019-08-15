package org.luisa.miniwrangler.java.transform;

/**
 * An interface that describes the contract for objects ITextTransform objects,
 * which should represent a text transformation
 *
 * @param <Facade> A facade to interact with the type of objects that will be
 *                 transformed
 */
public interface ITextTransformRunner<Facade> {

    /*
     * TODO: Should complete this interface and its implementation as a concrete
     * ITextTransformRunner<Order> as a subscriber that would be interested in hear
     * about CSVProcessor subjects such as the appearance of new source field value
     * map and subsequently create an order and run transformations on that order
     * with each new source field value map
     */
}
