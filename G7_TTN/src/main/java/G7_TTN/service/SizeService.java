package G7_TTN.service;

import G7_TTN.entity.Size;
import G7_TTN.reponsitory.SizeReponsitory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeService {

    private final SizeReponsitory repo;

    public SizeService(SizeReponsitory repo) {
        this.repo = repo;
    }

    public List<Size> getAll() {
        return repo.findAll();
    }
}