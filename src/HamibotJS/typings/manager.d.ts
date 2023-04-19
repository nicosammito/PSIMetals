export class CacheManager<T> {
    get cache(): Map<string, T | undefined>
}