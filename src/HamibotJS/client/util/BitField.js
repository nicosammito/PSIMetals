class BitField {

    /**
     * Data that can be resolved to give a bitfield. This can be:
     * * A bit number (this can be a number literal or a value taken from {@link BitField.FLAGS})
     * * A string bit number
     * * An instance of BitField
     * * An Array of BitFieldResolvable
     * @typedef {number|string|bigint|BitField|BitFieldResolvable[]} BitFieldResolvable
     */

    /**
     * @param {BitFieldResolvable} [bits=this.constructor.defaultBit] Bit(s) to read from
     */
    constructor(bits = this.constructor.defaultBit) {
        /**
         * Bitfield of the packed bits
         * @type {number|bigint}
         */
        this.bitfield = this.resolve(bits);
    }

    /**
     * Checks whether the bitfield has a bit, or any of multiple bits.
     * @param {BitFieldResolvable} bit Bit(s) to check for
     * @returns {boolean}
     */
    any(bit) {
        return (this.bitfield & this.resolve(bit)) !== this.constructor.defaultBit;
    }

    /**
     * Checks if this bitfield equals another
     * @param {BitFieldResolvable} bit Bit(s) to check for
     * @returns {boolean}
     */
    equals(bit) {
        return this.bitfield === this.resolve(bit);
    }

    /**
     * Checks whether the bitfield has a bit, or multiple bits.
     * @param {BitFieldResolvable} bit Bit(s) to check for
     * @returns {boolean}
     */
    has(bit) {
        bit = this.resolve(bit);
        return (this.bitfield & bit) === bit;
    }

    /**
     * Gets all given bits that are missing from the bitfield.
     * @param {BitFieldResolvable} bits Bit(s) to check for
     * @returns {string[]}
     */
    missing(bits) {
        return new this.constructor(bits).remove(this).toArray();
    }

    /**
     * Freezes these bits, making them immutable.
     * @returns {Readonly<BitField>}
     */
    freeze() {
        return Object.freeze(this);
    }

    /**
     * Adds bits to these ones.
     * @param {...BitFieldResolvable} [bits] Bits to add
     * @returns {BitField} These bits or new BitField if the instance is frozen.
     */
    add(...bits) {
        let total = this.constructor.defaultBit;
        for (const bit of bits) {
            total |= this.resolve(bit);
        }
        if (Object.isFrozen(this)) return new this.constructor(this.bitfield | total);
        this.bitfield |= total;
        return this;
    }

    /**
     * Removes bits from these.
     * @param {...BitFieldResolvable} [bits] Bits to remove
     * @returns {BitField} These bits or new BitField if the instance is frozen.
     */
    remove(...bits) {
        let total = this.constructor.defaultBit;
        for (const bit of bits) {
            total |= this.resolve(bit);
        }
        if (Object.isFrozen(this)) return new this.constructor(this.bitfield & ~total);
        this.bitfield &= ~total;
        return this;
    }

    /**
     * Gets an {@link Array} of bitfield names based on the bits available.
     * @returns {string[]}
     */
    toArray() {
        return Object.keys(this.constructor.FLAGS).filter(bit => this.has(bit));
    }

    valueOf() {
        return this.bitfield;
    }

    /**
     * Resolves bitfields to their numeric form.
     * @param {BitFieldResolvable} [bit] - bit(s) to resolve
     * @returns {number|bigint}
     */
    resolve(bit) {
        if (typeof this.constructor.defaultBit === typeof bit && bit >= this.constructor.defaultBit) return bit;
        if (bit instanceof BitField) return bit.bitfield;
        if (Array.isArray(bit)) return bit.map(p => this.resolve(p)).reduce((prev, p) => prev | p, this.constructor.defaultBit);
        if (typeof bit === 'string') {
            if (typeof this.constructor.FLAGS[bit] !== 'undefined') return this.constructor.FLAGS[bit];
            if (!isNaN(bit)) return typeof this.constructor.defaultBit === 'bigint' ? BigInt(bit) : Number(bit);
        }
    }
}

/**
 * Numeric bitfield flags.
 * <info>Defined in extension classes</info>
 * @type {Object}
 * @abstract
 */
BitField.FLAGS = {};

/**
 * @type {number|bigint}
 * @private
 */
BitField.defaultBit = 0;

module.exports = BitField;