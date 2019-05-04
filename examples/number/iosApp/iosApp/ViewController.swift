import UIKit
import app

class ViewController: UIViewController, NumberView {

    let presenter = NumberPresenter(numberInteractor: NumberInteractor())
    
    override func viewDidLoad() {
        super.viewDidLoad()
        presenter.attachView(view: self)
    }
    
    func render(viewState: CoreMviViewState) {
        let numberViewState = viewState as! NumberViewState
        label.text = String(numberViewState.number)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    @IBOutlet weak var label: UILabel!

    @IBAction func minusPressed() {
        presenter.onIntent(intent: NumberViewNumberIntent.MinusIntent())
    }
    
    @IBAction func plusPressed() {
        presenter.onIntent(intent: NumberViewNumberIntent.PlusIntent())
    }
}
