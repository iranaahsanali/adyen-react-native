//
//  JsonHelpers.swift
//  AdyenReactNative
//
//  Created by Vladimir Abramichev on 05/01/2022.
//

import Adyen
import Foundation

internal extension PaymentComponentData {
    var jsonObject: [String: Any] {
        EncodablePaymentComponentData(data: self).jsonDictionary
    }
}

internal extension PaymentMethod {
    var jsonObject: [String: Any] {
        EncodablePaymentMethod(paymentMethod: self).jsonDictionary
    }
}

internal extension ActionComponentData {
    var jsonObject: [String: Any] {
        EncodableActionData(data: self).jsonDictionary
    }
}

private extension Encodable {
    var jsonDictionary: [String: Any] {
        guard let data = try? JSONEncoder().encode(self),
              let object = try? JSONSerialization.jsonObject(with: data, options: []) as? [String: Any] else {
            return [:]
        }

        return object
    }
}

internal extension Error {

    var toDictionary: [String: Any] {
        var dict = ["message": self.localizedDescription]

        if let localized = self as? LocalizedError {
            dict["reason"] = localized.failureReason
            dict["discription"] = localized.errorDescription
            dict["recovery"] = localized.recoverySuggestion
        }

        return dict
    }
}
