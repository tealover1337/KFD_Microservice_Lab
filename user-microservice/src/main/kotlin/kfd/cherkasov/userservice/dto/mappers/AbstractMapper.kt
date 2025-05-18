package kfd.cherkasov.userservice.dto.mappers

interface AbstractMapper<in E, out R> {
    fun mapEntityToResponse(entity: E): R
}