package com.depromeet.threedollar.api.service.store;

import com.depromeet.threedollar.api.service.UserSetUpTest;
import com.depromeet.threedollar.api.service.store.dto.request.DeleteStoreRequest;
import com.depromeet.threedollar.domain.domain.store.Store;
import com.depromeet.threedollar.domain.domain.store.StoreCreator;
import com.depromeet.threedollar.domain.domain.store.StoreRepository;
import com.depromeet.threedollar.domain.domain.storedelete.DeleteReasonType;
import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequest;
import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequestCreator;
import com.depromeet.threedollar.domain.domain.storedelete.StoreDeleteRequestRepository;
import com.depromeet.threedollar.domain.exception.ConflictException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class StoreDeleteServiceTest extends UserSetUpTest {

	@Autowired
	private StoreDeleteService storeDeleteRequestService;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private StoreDeleteRequestRepository storeDeleteRequestRepository;

	@AfterEach
	void cleanUp() {
		super.cleanup();
		storeRepository.deleteAll();
		storeDeleteRequestRepository.deleteAll();
	}

	@Test
	void 해당_가게에_삭제요청이_오면_Store_Delete_Request_가_하나_추가된다() {
		// given
		Store store = StoreCreator.create(userId, "storeName");
		storeRepository.save(store);

		DeleteReasonType type = DeleteReasonType.NOSTORE;

		// when
		storeDeleteRequestService.delete(store.getId(), DeleteStoreRequest.testInstance(DeleteReasonType.NOSTORE), userId);

		// then
		List<StoreDeleteRequest> storeDeleteRequestList = storeDeleteRequestRepository.findAll();
		assertThat(storeDeleteRequestList).hasSize(1);
		assertStoreDeleteRequest(storeDeleteRequestList.get(0), store.getId(), userId, type);
	}

	@Test
	void 해당_사용자가_해당하는_가게에_대해_이미_삭제요청_한경우_에러가_발생한다() {
		// given
		Store store = StoreCreator.create(userId, "storeName");
		storeRepository.save(store);

		storeDeleteRequestRepository.save(StoreDeleteRequestCreator.create(store.getId(), userId, DeleteReasonType.NOSTORE));

		// when & then
		assertThatThrownBy(() -> storeDeleteRequestService.delete(store.getId(), DeleteStoreRequest.testInstance(DeleteReasonType.NOSTORE), userId)).isInstanceOf(ConflictException.class);
	}

	@Test
	void 해당_가게에_삭제요청이_와서_두개가_되면_Store_Delete_Request_가_하나_추가된다() {
		// given
		Store store = StoreCreator.create(userId, "storeName");
		storeRepository.save(store);

		storeDeleteRequestRepository.save(StoreDeleteRequestCreator.create(store.getId(), 90L, DeleteReasonType.WRONG_CONTENT));

		// when
		storeDeleteRequestService.delete(store.getId(), DeleteStoreRequest.testInstance(DeleteReasonType.NOSTORE), userId);

		// then
		List<StoreDeleteRequest> storeDeleteRequestList = storeDeleteRequestRepository.findAll();
		assertThat(storeDeleteRequestList).hasSize(2);
		assertStoreDeleteRequest(storeDeleteRequestList.get(0), store.getId(), 90L, DeleteReasonType.WRONG_CONTENT);
		assertStoreDeleteRequest(storeDeleteRequestList.get(1), store.getId(), userId, DeleteReasonType.NOSTORE);
	}

	@Test
	void 해당_가게에_삭제요청이_와서_세개가_되면_Store_Delete_Request_가_하나_추가되고_해당_가게는_삭제처리된다() {
		// given
		Store store = StoreCreator.create(userId, "storeName");
		storeRepository.save(store);

		storeDeleteRequestRepository.saveAll(Arrays.asList(
				StoreDeleteRequestCreator.create(store.getId(), 90L, DeleteReasonType.NOSTORE),
				StoreDeleteRequestCreator.create(store.getId(), 91L, DeleteReasonType.NOSTORE))
		);

		// when
		storeDeleteRequestService.delete(store.getId(), DeleteStoreRequest.testInstance(DeleteReasonType.NOSTORE), userId);

		// then
		List<Store> stores = storeRepository.findAll();
		assertThat(stores).hasSize(1);
		assertThat(stores.get(0).isDeleted()).isTrue();

		List<StoreDeleteRequest> storeDeleteRequestList = storeDeleteRequestRepository.findAll();
		assertThat(storeDeleteRequestList).hasSize(3);
	}

	private void assertStoreDeleteRequest(StoreDeleteRequest storeDeleteRequest, Long storeId, Long userId, DeleteReasonType type) {
		assertThat(storeDeleteRequest.getStoreId()).isEqualTo(storeId);
		assertThat(storeDeleteRequest.getUserId()).isEqualTo(userId);
		assertThat(storeDeleteRequest.getReason()).isEqualTo(type);
	}

}