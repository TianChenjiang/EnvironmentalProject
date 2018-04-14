
@Service
public class RecordBlService implements RecordBlService{
    private final RecordBlService recordBlService;

    @Autowired
    public RecordBlServiceimpl(RecordBlService recordBlService){this.recordBlService = recordBlService}

    @Override
    public void check(SpeciesCheckVo speciesCheckVo){
    }
}