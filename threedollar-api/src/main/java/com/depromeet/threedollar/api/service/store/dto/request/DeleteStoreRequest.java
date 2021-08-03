package com.depromeet.threedollar.api.service.store.dto.request;

import com.depromeet.threedollar.domain.domain.storedelete.DeleteReasonType;
import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequest;
import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteStoreRequest {

    @NotNull(message = "{store.delete.reason.notNull}")
    private DeleteReasonType deleteReasonType;

    public static DeleteStoreRequest testInstance(DeleteReasonType reasonType) {
        return new DeleteStoreRequest(reasonType);
    }

    public StoreDeleteRequest toEntity(Long storeId, Long userId) {
        return StoreDeleteRequest.of(storeId, userId, deleteReasonType);
    }

}
