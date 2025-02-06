package umc.pating.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.pating.repository.DailyRepository;
import umc.pating.repository.TestRepository;
import umc.pating.repository.UserRepository;
import umc.pating.services.MonthlyResponseDTO;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MonthlyService {
    private final DailyRepository dailyRepository;
    private final TestRepository testRepository;
    private final UserRepository userRepository;

//    public List<MonthlyResponseDTO> getMonthly(Long userId, int year, int month)

}
