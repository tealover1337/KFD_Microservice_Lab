package kfd.cherkasov.paymentmicroservice.exceptions

class NoContextException(message: String) : RuntimeException(message)
class RestrictedUserException(message: String) : RuntimeException(message)